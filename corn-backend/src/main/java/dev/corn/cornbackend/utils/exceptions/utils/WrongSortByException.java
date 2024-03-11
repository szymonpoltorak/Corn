package dev.corn.cornbackend.utils.exceptions.utils;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class WrongSortByException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 2773015095944947448L;

    private static final String MESSAGE = "Wrong sorting field or direction: %s";

    public WrongSortByException(String sortBy) {
        super(HttpStatus.BAD_REQUEST, String.format(MESSAGE, sortBy));
    }
}
