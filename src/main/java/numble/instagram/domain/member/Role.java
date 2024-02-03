package numble.instagram.domain.member;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER");

    private final String role;

    private List<String> getRoles() {
        return Arrays.asList(role.split(","));
    }
}
