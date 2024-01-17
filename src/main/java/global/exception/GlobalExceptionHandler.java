package global.exception;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.debug("Request validation error occurred: {}", e.getMessage(), e);
        return ErrorResponse.from(HttpStatus.BAD_REQUEST, getMessage(e));
    }

    private String getMessage(Exception e) {
        return Optional.ofNullable(e.getCause())
            .map(Throwable::getMessage)
            .orElse(e.getMessage());
    }

}
