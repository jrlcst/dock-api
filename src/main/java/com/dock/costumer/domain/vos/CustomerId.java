package com.dock.costumer.domain.vos;


import com.dock.common.exceptions.DomainException;

import java.util.UUID;

public record CustomerId(UUID value) {

    public static CustomerId newId() {
        return new CustomerId(UUID.randomUUID());
    }

    public static CustomerId from(String value) {

        if (value == null || value.isBlank()) {
            throw new DomainException("value cannot be null or blank");
        }

        return new CustomerId(UUID.fromString(value));
    }

    public String getValueString() {
        return value.toString();
    }

}
