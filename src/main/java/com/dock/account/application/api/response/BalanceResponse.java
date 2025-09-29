package com.dock.account.application.api.response;

import com.dock.account.domain.models.Account;
import java.math.BigDecimal;

public record BalanceResponse(
        String accountId,
        BigDecimal balance,
        String status
) {
    public static BalanceResponse from(final Account account) {
        return new BalanceResponse(
                account.getId().getValueString(),
                account.getBalance().value(),
                account.getStatus().name()
        );
    }
}
