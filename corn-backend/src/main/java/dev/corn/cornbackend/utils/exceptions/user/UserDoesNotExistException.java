package dev.corn.cornbackend.utils.exceptions.user;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatusCode;

import java.io.Serial;

public class UserDoesNotExistException extends AbstractException {
    @Serial
    private static final long serialVersionUID = 5384424316620279322L;

    public UserDoesNotExistException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
