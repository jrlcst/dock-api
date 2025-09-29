package com.dock.account.domain.vos;

import com.dock.common.exceptions.DomainException;

import java.util.Objects;

public record AgencyNumber(String value) {
    public AgencyNumber {
        Objects.requireNonNull(value, "value");
        String trimmed = value.trim();
        if (!trimmed.matches("[0-9]{4}")) {
            throw new DomainException("Agência deve ter 4 dígitos");
        }
        value = trimmed;
    }
}
