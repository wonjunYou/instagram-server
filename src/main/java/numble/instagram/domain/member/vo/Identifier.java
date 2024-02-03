package numble.instagram.domain.member.vo;

import jakarta.persistence.Column;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Identifier {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 20;
    private static final String REGEX = "^[a-z0-9]+$";

    @Column(length = 50, unique = true, nullable = false)
    private String identifier;

    public Identifier(final String identifier) {
        validate(identifier);
        this.identifier = identifier;
    }

    private void validate(String identifier) {
        if (isNotValidLength(identifier) || isNotValidPattern(identifier)) {
            throw new IllegalArgumentException("제약 조건에 맞지 않는 아이디입니다.");
        }
    }

    private boolean isNotValidLength(String identifier) {
        return identifier.length() < MIN_LENGTH || identifier.length() > MAX_LENGTH;
    }

    private boolean isNotValidPattern(String identifier) {
        return !identifier.matches(REGEX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identifier that = (Identifier) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
