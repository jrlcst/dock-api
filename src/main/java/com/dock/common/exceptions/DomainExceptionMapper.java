package com.dock.common.exceptions;

import com.dock.common.application.ApiError;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DomainExceptionMapper implements ExceptionMapper<DomainException> {

    private static final int UNPROCESSABLE_ENTITY_STATUS_CODE = 422;

    @Override
    public Response toResponse(DomainException exception) {
        var error = ApiError.of(
                UNPROCESSABLE_ENTITY_STATUS_CODE,
                "Unprocessable Entity",
                exception.getMessage()
        );
        return Response.status(UNPROCESSABLE_ENTITY_STATUS_CODE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(error)
                .build();
    }
}
