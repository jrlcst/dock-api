package com.dock.account.domain.vos;

import com.dock.common.exceptions.DomainException;

import java.util.UUID;

public record AccountId(UUID value) {

    public AccountId {
        if (value == null) {
            throw new DomainException("Account id cannot be null");
        }
    }

    public static AccountId newId() {
        return new AccountId(UUID.randomUUID());
    }

    public static AccountId from(String value) {
        if (value == null || value.isBlank()) {
            throw new DomainException("value cannot be null or blank");
        }
        return new AccountId(UUID.fromString(value));
    }

    public String getValueString() {
        return value.toString();
    }
}
