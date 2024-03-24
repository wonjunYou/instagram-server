package numble.instagram.spring.api.member.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import numble.instagram.api.member.controller.MemberController;
import numble.instagram.api.member.controller.request.MemberJoinRequest;
import numble.instagram.api.member.service.MemberService;
import numble.instagram.spring.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(MemberController.class)
@WithMockUser
public class MemberControllerTest extends ControllerTestSupport {

    @MockBean
    private MemberService memberService;

    @DisplayName("신규 회원을 등록한다.")
    @Test
    void joinMember() throws Exception {
        // given
        MemberJoinRequest request = new MemberJoinRequest(
            "identifier1", "password1!",
            "test@naver.com", "test",
            "테스터1", "MAN",
            "010-1234-5678"
        );

        // when & then
        mockMvc.perform(
                post("/api/members/join")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk())
            .andDo(print());
    }
}
