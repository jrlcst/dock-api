package com.dock.account.application.commands.handler;

import com.dock.account.application.api.response.BalanceResponse;
import com.dock.account.application.commands.DepositCommand;
import com.dock.account.domain.models.Account;
import com.dock.account.domain.vos.AccountId;
import com.dock.account.domain.repositories.AccountRepository;
import com.dock.account.domain.vos.Amount;
import com.dock.common.commands.CommandHandler;
import com.dock.common.exceptions.DomainException;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@Unremovable
@RequiredArgsConstructor
public class DepositHandler implements CommandHandler<DepositCommand, BalanceResponse> {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public BalanceResponse handle(DepositCommand command) {
        final Account account = accountRepository.findById(AccountId.from(command.accountId()))
                .orElseThrow(() -> new DomainException("Conta não encontrada"));

        account.deposit(Amount.of(command.amount()), command.description());
        accountRepository.update(account);

        return BalanceResponse.from(account);
    }
}
