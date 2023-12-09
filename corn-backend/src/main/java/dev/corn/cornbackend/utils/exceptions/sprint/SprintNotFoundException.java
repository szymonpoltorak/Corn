package dev.corn.cornbackend.utils.exceptions.sprint;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class SprintNotFoundException extends AbstractException {

    public SprintNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
