package com.dock.account.domain.vos;

import java.util.Objects;

public record AccountNumber(String value) {
    public AccountNumber {
        Objects.requireNonNull(value, "value");
        String trimmed = value.trim();
        if (!trimmed.matches("[0-9]{6,12}")) {
            throw new IllegalArgumentException("Número da conta deve ter entre 6 e 12 dígitos");
        }
        value = trimmed;
    }
}
