package com.dock.account.domain.repositories;

import com.dock.account.domain.models.Account;
import com.dock.account.domain.vos.AccountId;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(AccountId id);
    Optional<Account> findByCustomerId(String customerId);
    Optional<Account> findByAgencyAndNumberAndCostumerId(String agency, String number, String costumerId);
    BigDecimal getTotalWithdrawalForAccountToday(AccountId accountId);
    void save(Account account);
    void update(Account account);
}
