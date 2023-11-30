package dev.corn.cornbackend.utils.exceptions.project;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatusCode;

import java.io.Serial;

public class ProjectDoesNotExistException extends AbstractException {
    @Serial
    private static final long serialVersionUID = -6292494638371255985L;

    public ProjectDoesNotExistException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
