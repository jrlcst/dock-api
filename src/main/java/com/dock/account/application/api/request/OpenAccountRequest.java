package com.dock.account.application.api.request;

import jakarta.validation.constraints.NotBlank;

public record OpenAccountRequest(
        @NotBlank String document
) {
}
