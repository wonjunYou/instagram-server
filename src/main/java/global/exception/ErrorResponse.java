package global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse from(HttpStatus status, String message) {
        return new ErrorResponse(status.value(), message);
    }
}
