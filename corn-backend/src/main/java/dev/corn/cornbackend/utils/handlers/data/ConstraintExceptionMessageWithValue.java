package dev.corn.cornbackend.utils.handlers.data;

import lombok.Builder;

@Builder
public record ConstraintExceptionMessageWithValue(String errorMessage, String invalidValue) {
}
