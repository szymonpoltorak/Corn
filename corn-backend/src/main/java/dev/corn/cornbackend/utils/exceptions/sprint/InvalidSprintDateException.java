package dev.corn.cornbackend.utils.exceptions.sprint;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Exception thrown when the end date of a sprint is before the start date
 */
public class InvalidSprintDateException extends AbstractException {
    @Serial
    private static final long serialVersionUID = -3276513929931218115L;

    public InvalidSprintDateException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
