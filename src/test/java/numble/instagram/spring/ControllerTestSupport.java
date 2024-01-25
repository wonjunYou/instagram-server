package numble.instagram.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import numble.instagram.api.member.controller.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    MemberController.class
})
public abstract class ControllerTestSupport {

    public static final String API_PREFIX = "/api";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
