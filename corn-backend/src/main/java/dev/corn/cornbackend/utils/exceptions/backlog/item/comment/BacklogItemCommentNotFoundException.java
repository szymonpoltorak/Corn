package dev.corn.cornbackend.utils.exceptions.backlog.item.comment;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class BacklogItemCommentNotFoundException extends AbstractException {

    public BacklogItemCommentNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
