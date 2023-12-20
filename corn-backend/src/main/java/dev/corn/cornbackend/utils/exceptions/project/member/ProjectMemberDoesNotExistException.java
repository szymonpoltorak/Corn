package dev.corn.cornbackend.utils.exceptions.project.member;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ProjectMemberDoesNotExistException extends AbstractException {
    @Serial
    private static final long serialVersionUID = -1449980180213017793L;

    public ProjectMemberDoesNotExistException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
