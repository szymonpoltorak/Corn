package dev.corn.cornbackend.utils.exceptions.sprint;

import dev.corn.cornbackend.utils.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class SprintEndDateMustBeAfterStartDate extends AbstractException {

    public SprintEndDateMustBeAfterStartDate(LocalDate startDate, LocalDate endDate) {
        super(HttpStatus.BAD_REQUEST, String.format(
                "Sprint end date must be after start date: given start date '%s' is after end date '%s'",
                startDate, endDate
        ));
    }
}
