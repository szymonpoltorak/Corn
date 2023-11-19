package dev.corn.cornbackend.utils.handlers.data;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ConstraintExceptionResponse(String validationErrorClassName,
                                          List<ConstraintExceptionMessageWithValue> errorList,
                                          LocalDate timeStamp) {
}
