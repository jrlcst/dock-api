package com.dock.account.application.api.response;

import com.dock.account.domain.models.Account;
import com.dock.account.domain.models.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record StatementResponse(
        String accountId,
        Instant periodStart,
        Instant periodEnd,
        List<TransactionResponse> transactions,
        BigDecimal balance
) {
    public static StatementResponse from(
            final Account account,
            final Instant start,
            final Instant end,
            List<Transaction> transactions
    ) {
        return new StatementResponse(
                account.getId().getValueString(),
                start,
                end,
                transactions.stream().map(TransactionResponse::from).toList(),
                account.getBalance().value()
        );
    }
}
