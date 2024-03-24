package numble.instagram.spring.api.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;
import numble.instagram.api.auth.controller.dto.request.LoginRequest;
import numble.instagram.api.auth.controller.dto.request.ReissueTokenRequest;
import numble.instagram.api.auth.controller.dto.response.AuthenticationResponse;
import numble.instagram.api.auth.service.AuthService;
import numble.instagram.common.exception.AuthenticationException;
import numble.instagram.common.jwt.JwtTokenProvider;
import numble.instagram.domain.auth.EncryptedToken;
import numble.instagram.domain.auth.RefreshToken;
import numble.instagram.domain.auth.RefreshTokenRepository;
import numble.instagram.domain.member.GenderType;
import numble.instagram.domain.member.Member;
import numble.instagram.domain.member.MemberRepository;
import numble.instagram.domain.member.vo.EncodedPassword;
import numble.instagram.domain.member.vo.Identifier;
import numble.instagram.domain.member.vo.Password;
import numble.instagram.domain.memberprofile.MemberProfile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private static Member member;
    private static MemberProfile memberProfile;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeAll
    static void setUp() {
        memberProfile = createMemberProfile(
            "test@gmail.com",
            "insta",
            "nickname",
            GenderType.MAN,
            "010-1234-5678"
        );

        member = createMember(
            new Identifier("identifier1"),
            new Password("password1"),
            memberProfile
        );
    }

    @DisplayName("로그인에 성공한다.")
    @Test
    void loginSuccess() {
        // given
        LoginRequest loginRequest = new LoginRequest("identifier1", "password1");
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        given(memberRepository.findByIdentifier(any()))
            .willReturn(Optional.of(member));

        given(jwtTokenProvider.createAccessToken(any(), any()))
            .willReturn(accessToken);

        given(jwtTokenProvider.createRefreshToken(any(), any()))
            .willReturn(refreshToken);

        given(jwtTokenProvider.findTokenExpiredAt(anyString()))
            .willReturn(LocalDateTime.now());

        // when
        AuthenticationResponse authenticationResponse = authService.login(loginRequest);

        // then
        assertThat(authenticationResponse.accessToken()).isEqualTo(accessToken);
        assertThat(authenticationResponse.refreshToken()).isEqualTo(refreshToken);
    }

    @DisplayName("존재하지 않는 아이디로 로그인 시, 예외가 발생한다.")
    @Test
    void loginWithNotExistIdentifier() {
        // given
        final LoginRequest loginRequest = new LoginRequest("identifier1", "password1!");

        given(memberRepository.findByIdentifier(any()))
            .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authService.login(loginRequest))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("존재하지 않는 아이디입니다.");
    }

    @DisplayName("유효하지 않은 비밀번호로 로그인 시, 예외가 발생한다.")
    @Test
    void loginWithInvalidPassword() {
        // given
        final LoginRequest loginRequest = new LoginRequest("identifier1", "illegal");

        // when & then
        assertThatThrownBy(() -> authService.login(loginRequest))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("제약 조건에 맞지 않는 비밀번호입니다.");
    }

    @DisplayName("Refresh Token을 정상적으로 재 발급한다.")
    @Test
    void reissueToken() {
        // given
        String rawRefreshToken = "refreshToken";

        ReissueTokenRequest request = new ReissueTokenRequest("refreshToken");

        RefreshToken refreshToken = new RefreshToken(new EncryptedToken(rawRefreshToken),
            LocalDateTime.MAX, member);

        given(jwtTokenProvider.isValidToken(any()))
            .willReturn(true);

        given(refreshTokenRepository.findByTokenAndIsRevokedFalse(any()))
            .willReturn(Optional.of(refreshToken));

        given(jwtTokenProvider.createRefreshToken(any(), any()))
            .willReturn(rawRefreshToken);

        given(jwtTokenProvider.findTokenExpiredAt(anyString()))
            .willReturn(LocalDateTime.now());

        // when
        AuthenticationResponse authenticationResponse = authService.reissueToken(request);

        // then
        assertThat(authenticationResponse.refreshToken()).isEqualTo(rawRefreshToken);
    }

    @DisplayName("리프레시 토큰이 유효하지 않을 경우, 예외가 발생한다.")
    @Test
    void invalidRefreshToken() {
        // given
        String rawRefreshToken = "refreshToken";
        ReissueTokenRequest request = new ReissueTokenRequest(rawRefreshToken);

        given(jwtTokenProvider.isValidToken(any()))
            .willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.reissueToken(request))
            .isInstanceOf(AuthenticationException.class);
    }

    @DisplayName("리프레시 토큰이 만료된 경우, 예외가 발생한다.")
    @Test
    void timeoutRefreshToken() {
        // given
        String rawRefreshToken = "refreshToken";
        ReissueTokenRequest request = new ReissueTokenRequest(rawRefreshToken);
        RefreshToken refreshToken = new RefreshToken(new EncryptedToken(rawRefreshToken), LocalDateTime.MIN, member);


        given(jwtTokenProvider.isValidToken(any()))
            .willReturn(true);

        given(refreshTokenRepository.findByTokenAndIsRevokedFalse(any()))
            .willReturn(Optional.of(refreshToken));

        // when & then
        assertThatThrownBy(() -> authService.reissueToken(request))
            .isInstanceOf(AuthenticationException.class);
    }

    private static MemberProfile createMemberProfile(
        String email,
        String nickname,
        String name,
        GenderType genderType,
        String phoneNumber
    ) {

        return MemberProfile.builder()
            .email(email)
            .nickname(nickname)
            .name(name)
            .genderType(genderType)
            .phoneNumber(phoneNumber)
            .build();
    }

    private static Member createMember(
        Identifier identifier,
        Password password,
        MemberProfile memberProfile
    ) {

        EncodedPassword encodedPassword = new EncodedPassword(password);

        return Member.builder()
            .identifier(identifier)
            .password(encodedPassword)
            .memberProfile(memberProfile)
            .build();
    }
}
