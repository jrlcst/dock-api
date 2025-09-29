package com.dock.account.application.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.QueryParam;

public record GetStatementRequest(

        @QueryParam("start")
        @NotBlank
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "start must be in format yyyy-MM-dd")
        String start,

        @QueryParam("end")
        @NotBlank
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "end must be in format yyyy-MM-dd")
        String end
) {
}
