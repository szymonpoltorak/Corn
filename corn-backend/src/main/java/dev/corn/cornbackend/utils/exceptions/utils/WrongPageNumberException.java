package dev.corn.cornbackend.utils.exceptions.utils;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class WrongPageNumberException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 6996570131474872845L;

    private static final String DEFAULT_MESSAGE = "Incorrect page number: %d";

    public WrongPageNumberException(int pageNumber) {
        super(HttpStatus.BAD_REQUEST, String.format(DEFAULT_MESSAGE, pageNumber));
    }
}
