package dev.corn.cornbackend.utils.exceptions.project.member;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class InvalidUsernameException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -5186342194953918787L;

    public InvalidUsernameException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
