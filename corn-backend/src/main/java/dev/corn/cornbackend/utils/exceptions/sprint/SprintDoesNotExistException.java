package dev.corn.cornbackend.utils.exceptions.sprint;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class SprintDoesNotExistException extends AbstractException {
    @Serial
    private static final long serialVersionUID = -1545562156954734880L;

    public SprintDoesNotExistException(long id) {
        super(HttpStatus.NOT_FOUND, String.format(
                "Sprint with id %d does not exist",
                id
        ));
    }
}
