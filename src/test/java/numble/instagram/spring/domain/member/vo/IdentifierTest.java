package numble.instagram.spring.domain.member.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import numble.instagram.domain.member.vo.Identifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class IdentifierTest {

    @ParameterizedTest
    @ValueSource(strings = {"test12", "testtest12341234", "testtesttesttesttest"})
    @DisplayName("길이, 패턴 제약 조건을 만족하는 경우, 아이디가 정상적으로 생성된다.")
    void generateSuccessIdentifier(String id) {
        // expect
        assertThatCode(() -> new Identifier(id))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"short", "test"})
    @DisplayName("최소 길이 조건보다 짧은 id로 가입하려는 경우, 예외가 발생한다.")
    void generateLessThanMinimumLength(String id) {

        // expect
        assertThatThrownBy(() -> new Identifier(id))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("제약 조건에 맞지 않는 아이디입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"instagraminstagram123", "instagraminstagram1234567"})
    @DisplayName("최대 길이 조건보다 긴 id로 가입하려는 경우, 예외가 발생한다.")
    void generateMoreThanMaximumLength(String id) {

        // expect
        assertThatThrownBy(() -> new Identifier(id))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("제약 조건에 맞지 않는 아이디입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"INSTAGRAM", "instagram@#"})
    @DisplayName("아이디에 소문자나 숫자를 제외한 문자가 포함된 경우, 예외가 발생한다.")
    void isNotValidPattern(String id) {

        // expect
        assertThatThrownBy(() -> new Identifier(id))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("제약 조건에 맞지 않는 아이디입니다.");
    }
}
