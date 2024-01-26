package numble.instagram.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import numble.instagram.api.member.controller.MemberController;
import numble.instagram.common.config.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    MemberController.class
},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    }
)

public abstract class ControllerTestSupport {

    public static final String API_PREFIX = "/api";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
