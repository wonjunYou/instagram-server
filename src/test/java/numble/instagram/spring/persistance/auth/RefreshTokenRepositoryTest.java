package numble.instagram.spring.persistance.auth;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
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
import numble.instagram.spring.persistance.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class RefreshTokenRepositoryTest{

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("정상적으로 취소되지 않은 리프레시 토큰을 찾는다.")
    @Test
    void findByTokenAndIsRevokedFalse() {
        // given
        MemberProfile memberProfile = createMemberProfile(
            "test@gmail.com",
            "insta",
            "nickname",
            GenderType.MAN,
            "010-1234-5678"
        );

        Member member = createMember(
            new Identifier("identifier1"),
            new Password("password1"),
            memberProfile
        );

        EncryptedToken encryptedToken = new EncryptedToken("refreshToken");
        LocalDateTime now = LocalDateTime.now();

        RefreshToken refreshToken = new RefreshToken(encryptedToken, now, member);

        memberRepository.save(member);
        refreshTokenRepository.save(refreshToken);

        // when
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByTokenAndIsRevokedFalse(encryptedToken);

        // then
        assertThat(optionalRefreshToken).isNotEmpty();

        RefreshToken result = optionalRefreshToken.get();

        assertThat(result.getToken()).usingRecursiveComparison()
            .isEqualTo(encryptedToken);
    }

    private MemberProfile createMemberProfile(
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

    private Member createMember(
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
