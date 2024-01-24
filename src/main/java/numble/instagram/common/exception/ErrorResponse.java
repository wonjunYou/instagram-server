package numble.instagram.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final String message;

    private ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }
}
