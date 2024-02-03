package numble.instagram.domain.member.vo;

import jakarta.persistence.Column;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import numble.instagram.common.exception.ServerException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EncodedPassword {

    private static final String ALGORITHM = "SHA-256";

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String salt;

    public EncodedPassword(Password rawPassword) {
        try {
            this.salt = generateSalt(rawPassword.length());
            this.password = encode(rawPassword, salt);
        } catch (NoSuchAlgorithmException exception) {
            throw new ServerException(exception.getMessage());
        }
    }

    private String generateSalt(int length) {
        final SecureRandom secureRandom = new SecureRandom();
        final byte[] value = new byte[length];
        secureRandom.nextBytes(value);
        return Base64.getEncoder().encodeToString(value);
    }

    private String encode(Password password, String salt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        messageDigest.update(salt.getBytes());

        messageDigest.update(password.getBytes());

        final byte[] hashPassword = messageDigest.digest();

        return Base64.getEncoder().encodeToString(hashPassword);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EncodedPassword that = (EncodedPassword) o;
        return Objects.equals(password, that.password) && Objects.equals(salt,
            that.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, salt);
    }
}