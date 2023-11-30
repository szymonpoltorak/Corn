package dev.corn.cornbackend.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public abstract class AbstractException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = -7663571089805042815L;

    public AbstractException(String reason) {
        super(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), reason);
    }

    public AbstractException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public AbstractException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
