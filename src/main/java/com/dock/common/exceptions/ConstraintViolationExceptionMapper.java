package com.dock.common.exceptions;

import com.dock.common.application.ApiError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ApiError.FieldError> fieldErrors = exception.getConstraintViolations()
                .stream()
                .map(this::toFieldError)
                .toList();

        var error = ApiError.of(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Bad Request",
                "Validation failed",
                fieldErrors
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(error)
                .build();
    }

    private ApiError.FieldError toFieldError(ConstraintViolation<?> violation) {
        String field = "";
        if (violation.getPropertyPath() != null) {
            String path = violation.getPropertyPath().toString();
            int idx = path.lastIndexOf('.');
            field = idx >= 0 ? path.substring(idx + 1) : path;
        }
        return new ApiError.FieldError(field, violation.getMessage());
    }
}
