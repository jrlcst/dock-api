package com.dock.account.application.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DepositRequest(
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
        @Size(max = 255) String description
) {
}
