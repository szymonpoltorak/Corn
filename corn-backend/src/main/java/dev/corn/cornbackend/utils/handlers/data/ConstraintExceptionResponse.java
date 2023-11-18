package dev.corn.cornbackend.utils.handlers.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ConstraintExceptionResponse(String validationErrorClassName,
                                          String errorMessage,
                                          String invalidValue,
                                          LocalDate timeStamp) {
}
