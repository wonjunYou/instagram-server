package numble.instagram.domain.auth;

import jakarta.persistence.Column;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.instagram.common.exception.ServerException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EncryptedToken {
    private static final String ALGORITHM = "SHA-256";

    @Column(name = "token", nullable = false)
    private String value;

    public EncryptedToken(String token) {
        this.value = encrypt(token);
    }

    private String encrypt(String token) {
        MessageDigest messageDigest = findMessageDigest();
        messageDigest.update(token.getBytes());

        byte[] tokenHash = messageDigest.digest();

        return Base64.getEncoder().encodeToString(tokenHash);
    }

    private MessageDigest findMessageDigest() {
        try {
            return MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException exception) {
            throw new ServerException("존재하지 않는 알고리즘입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EncryptedToken that = (EncryptedToken) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
