package domain.member;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {

    // 최소 8 ~ 16글자, 소문자와 숫자 반드시 포함.
    private static final String PASSWORD_FORMAT = "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{8,16}$";

    private String password;

    private Password(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return Objects.equals(getPassword(), password.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword());
    }

    public static Password of(String password) {
        validatePassword(password);

        return new Password(password);
    }

    private static void validatePassword(String password) {
        if (!password.matches(PASSWORD_FORMAT)) {
            throw new IllegalArgumentException("잘못된 형식의 비밀번호입니다.");
        }
    }
}
