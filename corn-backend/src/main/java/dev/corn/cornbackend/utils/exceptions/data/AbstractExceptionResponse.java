package dev.corn.cornbackend.utils.exceptions.data;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AbstractExceptionResponse(String exceptionName, String exceptionMessage, LocalDateTime timeStamp){
}
