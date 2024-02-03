package numble.instagram.spring.api.member.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import numble.instagram.api.member.controller.request.MemberJoinRequest;
import numble.instagram.api.member.service.mapper.MemberService;
import numble.instagram.domain.member.MemberRepository;
import numble.instagram.domain.memberprofile.MemberProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberProfileRepository memberProfileRepository;

    @AfterEach
    void tearDown() {
        memberProfileRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("회원 정보를 받아 회원가입을 한다.")
    @Test
    void join() {
        // given

        MemberJoinRequest request = new MemberJoinRequest(
            "identifier1", "password1!",
            "test@naver.com", "test",
            "테스터1", "MAN",
            "010-1234-5678"
        );

        given(memberRepository.findByIdentifier(any()))
            .willReturn(Optional.empty());

        // when & then
        assertThatCode(() -> memberService.join(request))
            .doesNotThrowAnyException();
    }
}
