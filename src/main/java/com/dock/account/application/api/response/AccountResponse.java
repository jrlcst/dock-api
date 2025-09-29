package com.dock.account.application.api.response;

import com.dock.account.domain.enumerations.AccountStatus;
import com.dock.account.domain.models.Account;
import com.dock.costumer.domain.models.Customer;

import java.math.BigDecimal;

public record AccountResponse(
        String id,
        CustomerResponse customer,
        String agency,
        String number,
        AccountStatus status,
        BigDecimal balance
) {

    public record CustomerResponse(
            String fullName,
            String document
    ){}

    public static AccountResponse from(final Account account
    ) {

        final Customer customer = account.getCustomer();

        return new AccountResponse(
                account.getId().getValueString(),
                new CustomerResponse(customer.getFullName(), customer.getDocument().getValue()),
                account.getAgency().value(),
                account.getNumber().value(),
                account.getStatus(),
                account.getBalance().value()
        );
    }
}
