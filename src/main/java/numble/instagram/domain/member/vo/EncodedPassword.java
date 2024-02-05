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
        this.salt = generateSalt(rawPassword.length());
        this.password = encode(rawPassword, salt);
    }

    private String generateSalt(int length) {
        final SecureRandom secureRandom = new SecureRandom();
        final byte[] value = new byte[length];
        secureRandom.nextBytes(value);
        return Base64.getEncoder().encodeToString(value);
    }

    private String encode(Password password, String salt) {
        MessageDigest messageDigest = findMessageDigest();
        messageDigest.update(salt.getBytes());

        messageDigest.update(password.getBytes());

        final byte[] hashPassword = messageDigest.digest();

        return Base64.getEncoder().encodeToString(hashPassword);

    }

    private MessageDigest findMessageDigest() {
        try {
            return MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException exception) {
            throw new ServerException("존재하지 않는 알고리즘입니다.");
        }
    }

    public boolean isMismatch(final Password password) {
        final String encrypted = encode(password, this.salt);
        return !encrypted.equals(this.password);
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