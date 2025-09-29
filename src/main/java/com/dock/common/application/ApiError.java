package com.dock.common.application;

import java.time.Instant;
import java.util.List;

public record ApiError(
        int status,
        String error,
        String message,
        Instant timestamp,
        List<FieldError> errors
) {
    public static ApiError of(int status, String error, String message) {
        return new ApiError(status, error, message, Instant.now(), null);
    }

    public static ApiError of(int status, String error, String message, List<FieldError> errors) {
        return new ApiError(status, error, message, Instant.now(), errors);
    }

    public record FieldError(String field, String message) {}
}
