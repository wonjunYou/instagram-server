package numble.instagram.spring.domain.member.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import numble.instagram.domain.member.vo.Password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"test1234!", "hellohello1234"})
    @DisplayName("길이, 패턴 제약 조건을 만족하는 경우, 비밀번호가 정상적으로 생성된다.")
    void generatePassword(String password) {
        // expect
        assertThatCode(() -> new Password(password))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"short", "pwd"})
    @DisplayName("최소 길이 조건보다 짧은 비밀번호로 가입하려는 경우, 예외가 발생한다.")
    void generateLessThanMinimumLength(String password) {

        // expect
        assertThatThrownBy(() -> new Password(password))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("제약 조건에 맞지 않는 비밀번호입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"passwordpassword1", "passwordpasswordpassword"})
    @DisplayName("최대 길이 조건보다 긴 비밀번호로 가입하려는 경우, 예외가 발생한다.")
    void generateMoreThanMaximumLength(String password) {

        // expect
        assertThatThrownBy(() -> new Password(password))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("제약 조건에 맞지 않는 비밀번호입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"testTest@", "1234567890", "TESTNOTVALID99"})
    @DisplayName("비밀번호에 적어도 1개의 숫자와 1개의 소문자가 포함되지 않으면, 예외가 발생한다.")
    void isNotValidPattern(String password) {

        // expect
        assertThatThrownBy(() -> new Password(password))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("제약 조건에 맞지 않는 비밀번호입니다.");
    }
}
