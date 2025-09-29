package com.dock.account.domain.models;

import com.dock.account.domain.enumerations.TransactionType;
import com.dock.account.domain.vos.Amount;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Transaction(
        Long persistenceId,
        UUID id,
        TransactionType type,
        Amount amount,
        Instant occurredAt,
        String description
) {
    public Transaction {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(occurredAt);
        if (description != null && description.length() > 255) {
            throw new IllegalArgumentException("Descrição muito longa");
        }
    }

    public static Transaction credit(Amount amount, String description) {
        return new Transaction(null, UUID.randomUUID(), TransactionType.CREDIT, amount, Instant.now(), description);
    }

    public static Transaction debit(Amount amount, String description) {
        return new Transaction(null, UUID.randomUUID(), TransactionType.DEBIT, amount, Instant.now(), description);
    }
}
