package numble.instagram.api.auth.service;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import numble.instagram.api.auth.controller.dto.LoginDto;
import numble.instagram.api.auth.controller.dto.request.LoginRequest;
import numble.instagram.api.auth.controller.dto.request.ReissueTokenRequest;
import numble.instagram.api.auth.controller.dto.response.AuthenticationResponse;
import numble.instagram.api.auth.service.mapper.AuthMapper;
import numble.instagram.common.exception.AuthenticationException;
import numble.instagram.common.jwt.JwtTokenProvider;
import numble.instagram.domain.auth.EncryptedToken;
import numble.instagram.domain.auth.RefreshToken;
import numble.instagram.domain.auth.RefreshTokenRepository;
import numble.instagram.domain.member.Member;
import numble.instagram.domain.member.MemberRepository;
import numble.instagram.domain.member.vo.Password;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) {
        LoginDto loginDto = AuthMapper.convertToLoginDto(loginRequest);

        Member member = findMember(loginDto);

        checkPassword(loginDto.password(), member);

        return makeAuthenticationResponse(member);
    }

    @Transactional
    public AuthenticationResponse reissueToken(ReissueTokenRequest reissueTokenRequest) {
        checkTokenValid(reissueTokenRequest.refreshToken());

        EncryptedToken clientRefreshToken = AuthMapper.convertToEncryptedToken(reissueTokenRequest);
        RefreshToken refreshToken = findSavedRefreshToken(clientRefreshToken);
        checkTokenExpired(refreshToken);

        refreshTokenRepository.delete(refreshToken);

        Member member = refreshToken.getMember();

        return makeAuthenticationResponse(member);
    }

    private void checkTokenValid(String token) {
        if (!isCertified(token)) {
            throw new AuthenticationException("토큰이 유효하지 않습니다.");
        }
    }

    private RefreshToken findSavedRefreshToken(final EncryptedToken clientRefreshToken) {
        return refreshTokenRepository.findByTokenAndIsRevokedFalse(clientRefreshToken)
            .orElseThrow(() -> new AuthenticationException("토큰이 유효하지 않습니다."));
    }

    private void checkTokenExpired(final RefreshToken refreshToken) {
        if (refreshToken.isExpired()) {
            throw new AuthenticationException("토큰이 만료 되었습니다.");
        }
    }

    public String findIdentifierByToken(final String token) {
        return jwtTokenProvider.findSubject(token);
    }

    public boolean isCertified(String token) {
        return jwtTokenProvider.isValidToken(token);
    }

    private Member findMember(LoginDto loginDto) {
        return memberRepository.findByIdentifier(loginDto.identifier())
            .orElseThrow(() -> new AuthenticationException("존재하지 않는 아이디입니다."));
    }

    private void checkPassword(Password password, Member member) {
        if (member.isPasswordMismatch(password)) {

            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }
    }

    private AuthenticationResponse makeAuthenticationResponse(Member member) {
        String refreshToken = jwtTokenProvider.createRefreshToken(
            member.getIdentifier().getIdentifier(),
            Map.of());

        saveRefreshToken(member, refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(
            member.getIdentifier().getIdentifier(), Map.of());

        return AuthMapper.convertToAuthenticationResponse(refreshToken, accessToken);
    }

    private void saveRefreshToken(Member member, String rawRefreshToken) {
        EncryptedToken encryptedToken = new EncryptedToken(rawRefreshToken);

        LocalDateTime expiredAt = jwtTokenProvider.findTokenExpiredAt(rawRefreshToken);

        RefreshToken refreshToken = RefreshToken.builder()
            .token(encryptedToken)
            .expiredAt(expiredAt)
            .member(member)
            .build();

        refreshTokenRepository.save(refreshToken);
    }
}
