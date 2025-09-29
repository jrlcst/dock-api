package com.dock.account.domain.services;

import com.dock.account.domain.repositories.AccountRepository;
import com.dock.account.domain.vos.Amount;
import com.dock.account.domain.vos.AccountId;
import com.dock.common.exceptions.DomainException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class AccountValidateService {

    private final AccountRepository accountRepository;

    public void validateExistsAccountToCustomer(final String customerId) {
        if (accountRepository.findByCustomerId(customerId).isPresent()) {
            throw new DomainException("Já existe uma conta vinculada a este portador.");
        }
    }

    public Amount getTotalWithdrawalToday(AccountId accountId) {
        return Amount.of(accountRepository.getTotalWithdrawalForAccountToday(accountId));
    }
}
