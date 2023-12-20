package dev.corn.cornbackend.utils.exceptions.sprint;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class SprintDoesNotExistException extends AbstractException {

    public SprintDoesNotExistException(long id) {
        super(HttpStatus.NOT_FOUND, String.format(
                "Sprint with id %d does not exist",
                id
        ));
    }
}
