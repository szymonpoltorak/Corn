package dev.corn.cornbackend.utils.exceptions;

import dev.corn.cornbackend.utils.exceptions.data.AbstractExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class AbstractExceptionHandler {

    private static final String EXCEPTION_OCCURRED_MESSAGE = "Exception occurred with status: {} and response: {}";

    @ExceptionHandler(AbstractException.class)
    public final ResponseEntity<AbstractExceptionResponse> handleAbstractException(AbstractException exception) {
        AbstractExceptionResponse response = buildExceptionResponse(exception);

        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());

        log.error(EXCEPTION_OCCURRED_MESSAGE, status.value(), response);

        return ResponseEntity.status(status).body(response);
    }

    private AbstractExceptionResponse buildExceptionResponse(Exception exception) {
        return AbstractExceptionResponse.builder()
                .exceptionMessage(exception.getMessage())
                .exceptionName(exception.getClass().getSimpleName())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
