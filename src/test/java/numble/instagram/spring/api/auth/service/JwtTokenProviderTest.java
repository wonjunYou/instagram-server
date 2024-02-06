package numble.instagram.spring.api.auth.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.HashMap;
import java.util.Map;
import numble.instagram.common.exception.AuthenticationException;
import numble.instagram.common.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtTokenProviderTest {

    private static final String secretKey = "adminjsdfjj9o2184uoi8eur302udshfjnsadjfhjsdbfi0238u40hasdfjhk4!";

    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(secretKey, 1_080_000L, 8_640_000L);

    @BeforeAll
    static void beforeAll() {
        final String subject = "subject";
        final Map<String, Object> claims = new HashMap<>
            (Map.of("test1", "test1", "test2", "test2"));
    }


    @DisplayName("subject, claims를 포함한 ACCESS TOKEN을 정상적으로 생성한다.")
    @Test
    void createAccessToken() {
        // given
        final String subject = "subject";
        final Map<String, Object> claims = new HashMap<>
            (Map.of("test1", "test1", "test2", "test2"));

        // when
        String accessToken = jwtTokenProvider.createAccessToken(subject, claims);
        Claims result = getClaims(accessToken);

        // then

        assertThat(result.getSubject()).isEqualTo(subject);

        for (String claimKey : claims.keySet()) {
            String claim = result.get(claimKey, String.class);
            assertThat(claim).isEqualTo(claims.get(claimKey));
        }

        assertThatCode(result::getExpiration)
            .doesNotThrowAnyException();

    }

    @DisplayName("subject, claims를 포함한 REFRESH TOKEN을 정상적으로 생성한다.")
    @Test
    void createRefreshToken() {
        // given
        final String subject = "subject";
        final Map<String, Object> claims = new HashMap<>
            (Map.of("test1", "test1", "test2", "test2"));

        // when
        String refreshToken = jwtTokenProvider.createRefreshToken(subject, claims);

        // then
        Claims result = getClaims(refreshToken);

        assertThat(result.getSubject()).isEqualTo(subject);

        for (String claimKey : claims.keySet()) {
            String claim = result.get(claimKey, String.class);
        }

        assertDoesNotThrow(result::getExpiration);
    }

    @DisplayName("만료된 토큰의 경우, 유효성 검사에서 에러를 발생시킨다.")
    @Test
    void isValidTokenWithExpiredToken() {
        // given
        final String subject = "subject";
        final Map<String, Object> claims = new HashMap<>
            (Map.of("test1", "test1", "test2", "test2"));

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(secretKey, 0L, 0L);
        String accessToken = jwtTokenProvider.createAccessToken(subject, claims);

        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.isValidToken(accessToken))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("Expired Token");
    }

    @DisplayName("유효하지 않은 토큰을 사용하는 경우, 에러를 발생시킨다.")
    @Test
    void isValidTokenWithInvalidToken() {
        // given
        String accessToken = "invalid";

        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.isValidToken(accessToken))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("Invalid Token");
    }

    @DisplayName("토큰을 통해 subject를 가져온다.")
    @Test
    void findSubject() {
        // given
        final String subject = "subject";
        final Map<String, Object> claims = new HashMap<>
            (Map.of("test1", "test1", "test2", "test2"));

        String accessToken = jwtTokenProvider.createAccessToken(subject, claims);

        // when
        String result = jwtTokenProvider.findSubject(accessToken);

        // then
        assertThat(result).isEqualTo(subject);
    }



    private Claims getClaims(String accessToken) {
        return assertDoesNotThrow ( () ->
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
        );
    }
}
