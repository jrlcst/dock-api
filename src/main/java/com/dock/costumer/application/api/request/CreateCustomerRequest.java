package com.dock.costumer.application.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(

        @NotNull(message = "O nome não pode ser nulo")
        @NotBlank(message = "O nome não pode ser vazio")
        @Size(min = 3, max = 150, message = "O nome deve ter entre {min} e {max} caracteres")
        String fullName,

        @NotNull(message = "O documento não pode ser nulo")
        @NotBlank(message = "O documento não pode ser vazio")
        @Size(min = 11, max = 18, message = "O documento deve ter entre {min} e {max} caracteres")
        @Pattern(regexp = "^[0-9.\\-/]+$", message = "Documento deve conter apenas dígitos e .-/")
        String document
) {
}
