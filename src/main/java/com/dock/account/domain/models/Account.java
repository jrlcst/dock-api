package com.dock.account.domain.models;

import com.dock.account.domain.enumerations.AccountStatus;
import com.dock.account.domain.vos.Amount;
import com.dock.account.domain.vos.Period;
import com.dock.account.domain.vos.AccountId;
import com.dock.account.domain.vos.AccountNumber;
import com.dock.account.domain.vos.AgencyNumber;
import com.dock.common.exceptions.DomainException;
import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.vos.CustomerId;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Getter
public final class Account {

    public static final Amount DAILY_WITHDRAWAL_LIMIT = Amount.of(BigDecimal.valueOf(2000.00));

    private final AccountId id;
    private final Customer customer;
    private final CustomerId customerId;
    private final AccountNumber number;
    private final AgencyNumber agency;
    private AccountStatus status;
    private Amount balance;
    private final List<Transaction> transactions;

    private Account(AccountId id,
                    Customer customer,
                    CustomerId customerId,
                    AccountNumber number,
                    AgencyNumber agency,
                    AccountStatus status,
                    Amount balance,
                    List<Transaction> transactions
    ) {
        this.id = Objects.requireNonNull(id);
        this.customer = customer;
        this.customerId = Objects.requireNonNull(customerId);
        this.number = Objects.requireNonNull(number);
        this.agency = Objects.requireNonNull(agency);
        this.status = Objects.requireNonNull(status);
        this.balance = Objects.requireNonNull(balance);
        this.transactions = new ArrayList<>(Objects.requireNonNull(transactions));
    }

    public static Account open(final Customer customer,
                               final AccountNumber number,
                               final AgencyNumber agency
    ) {
        return new Account(
                AccountId.newId(),
                customer,
                customer.getId(),
                number,
                agency,
                AccountStatus.ACTIVE,
                Amount.ZERO,
                new ArrayList<>()
        );
    }

    public static Account from(AccountId id,
                               Customer customer,
                               CustomerId holderId,
                               AccountNumber number,
                               AgencyNumber agency,
                               AccountStatus status,
                               Amount balance,
                               List<Transaction> transactions
    ) {
        return new Account(id, customer, holderId, number, agency, status, balance, transactions);
    }

    private void ensureActiveUnblocked() {
        if (status == AccountStatus.CLOSED) {
            throw new DomainException("Conta está encerrada");
        }
        if (status == AccountStatus.BLOCKED) {
            throw new DomainException("Conta está bloqueada");
        }
    }

    public void deposit(final Amount amount,
                        final String description
    ) {
        if (isNull(amount)) {
            throw new DomainException("Valor nao pode ser nulo");
        }

        if (amount.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Depósito deve ser maior que zero");
        }
        ensureActiveUnblocked();
        this.balance = this.balance.add(amount);
        this.transactions.add(Transaction.credit(amount, description));
    }

    public void withdraw(final Amount amount,
                         final String description,
                         final Amount totalWithdrawalToday
    ) {

        if (isNull(amount)) {
            throw new DomainException("Valor nao pode ser nulo");
        }

        if (amount.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Saque deve ser maior que zero");
        }

        ensureActiveUnblocked();

        if (!this.balance.gte(amount)) {
            throw new DomainException("Saldo insuficiente");
        }

        final Amount newTotal = totalWithdrawalToday.add(amount);
        if (newTotal.gt(DAILY_WITHDRAWAL_LIMIT)) {
            throw new DomainException("Limite diário de saque excedido (R$ 2000.00)");
        }

        this.balance = this.balance.subtract(amount);
        this.transactions.add(Transaction.debit(amount, description));
    }

    public void block() {
        if (status == AccountStatus.CLOSED) {
            throw new DomainException("Conta já encerrada");
        }
        if (status == AccountStatus.BLOCKED) return;
        status = AccountStatus.BLOCKED;
    }

    public void unblock() {
        if (status == AccountStatus.CLOSED) {
            throw new DomainException("Conta já encerrada");
        }
        if (status == AccountStatus.ACTIVE) return;
        status = AccountStatus.ACTIVE;
    }

    public void close() {
        if (status == AccountStatus.CLOSED) return;
        if (this.balance.gt(Amount.ZERO)) {
            throw new DomainException("Não é possível encerrar conta com saldo positivo");
        }
        status = AccountStatus.CLOSED;
    }

    public List<Transaction> statement(Period period) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : this.transactions) {
            if (period.contains(t.occurredAt())) result.add(t);
        }
        return Collections.unmodifiableList(result);
    }
}
