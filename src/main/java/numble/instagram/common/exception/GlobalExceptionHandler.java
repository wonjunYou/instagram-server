package numble.instagram.common.exception;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception) {
        log.debug("Request validation error occurred: {}", exception.getMessage(), exception);

        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(getMessage(exception)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
        IllegalArgumentException exception) {
        log.debug("IllegalArgument error occurred: {}", exception.getMessage(), exception);

        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(getMessage(exception)));
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        log.error("Server Error occurred: {}", exception.getMessage(), exception);

        return ResponseEntity.internalServerError()
            .body(ErrorResponse.of(getMessage(exception)));
    }

    private String getMessage(Exception e) {
        return Optional.ofNullable(e.getCause())
            .map(Throwable::getMessage)
            .orElse(e.getMessage());
    }
}
