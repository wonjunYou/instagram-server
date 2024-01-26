package numble.instagram.spring.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import numble.instagram.domain.member.GenderType;
import numble.instagram.domain.member.Member;
import numble.instagram.domain.member.MemberRepository;
import numble.instagram.domain.member.vo.EncodedPassword;
import numble.instagram.domain.member.vo.Identifier;
import numble.instagram.domain.member.vo.Password;
import numble.instagram.domain.memberprofile.MemberProfile;
import numble.instagram.spring.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("아이디를 이용해 사용자를 조회한다.")
    @Test
    void findByIdentifier() {
        // given
        MemberProfile memberProfile = createMemberProfile(
            "test@gmail.com",
            "insta",
            "nickname",
            GenderType.MAN,
            "010-1234-5678"
        );

        Member member1 = createMember(
            new Identifier("identifier1"),
            new Password("password1"),
            memberProfile
        );

        memberRepository.save(member1);

        // when
        Optional<Member> findMember = memberRepository.findByIdentifier(new Identifier("identifier1"));

        // then
        assertThat(findMember).isNotEmpty();
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
