package numble.instagram.domain.member.vo;

import jakarta.persistence.Column;
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
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 16;

    private static final String REGEX = "^(?=.*[a-z])(?=.*\\d).{8,16}$";


    @Column(nullable = false)
    private String password;

    public Password(String password) {
        validatePassword(password);
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

    public int length() {
        return password.length();
    }

    public byte[] getBytes() {
        return password.getBytes();
    }

    private void validatePassword(String password) {
        if (isNotValidLength(password) || isNotValidPattern(password)) {
            throw new IllegalArgumentException("제약 조건에 맞지 않는 비밀번호입니다.");
        }
    }

    private boolean isNotValidLength(final String value) {
        return value.length() < MIN_LENGTH || value.length() > MAX_LENGTH;
    }

    private boolean isNotValidPattern(final String value) {
        return !value.matches(REGEX);
    }
}
