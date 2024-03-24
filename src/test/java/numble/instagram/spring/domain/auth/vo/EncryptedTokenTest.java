package numble.instagram.spring.domain.auth.vo;

import static org.assertj.core.api.Assertions.assertThat;

import numble.instagram.domain.auth.EncryptedToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EncryptedTokenTest {

    @ParameterizedTest
    @ValueSource(strings = {"test", "test2", "1234", "tokentoken"})
    @DisplayName("토큰 암호화에 성공한다.")
    void encryptToken(String rawToken) {
        EncryptedToken encryptedToken = new EncryptedToken(rawToken);

        assertThat(encryptedToken.getValue()).isNotEqualTo(rawToken);
    }
}
