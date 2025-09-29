package com.dock.account.application.api.response;

import com.dock.account.domain.models.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String type,
        BigDecimal amount,
        Instant occurredAt,
        String description
) {
    public static TransactionResponse from(final Transaction transaction) {
        return new TransactionResponse(
                transaction.id(),
                transaction.type().name(),
                transaction.amount().value(),
                transaction.occurredAt(),
                transaction.description()
        );
    }
}
