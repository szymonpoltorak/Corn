package dev.corn.cornbackend.utils.exceptions.backlog.item;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class BacklogItemNotFoundException extends AbstractException {

    public BacklogItemNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
