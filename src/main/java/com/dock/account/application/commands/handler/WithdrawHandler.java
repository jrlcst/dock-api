package com.dock.account.application.commands.handler;

import com.dock.account.application.api.response.BalanceResponse;
import com.dock.account.application.commands.WithdrawCommand;
import com.dock.account.domain.models.Account;
import com.dock.account.domain.services.AccountValidateService;
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
public class WithdrawHandler implements CommandHandler<WithdrawCommand, BalanceResponse> {

    private final AccountRepository accountRepository;
    private final AccountValidateService accountValidateService;

    @Override
    @Transactional
    public BalanceResponse handle(WithdrawCommand command) {
        final Account account = accountRepository.findById(AccountId.from(command.accountId()))
                .orElseThrow(() -> new DomainException("Conta não encontrada"));

        final Amount totalWithdrawalToday = accountValidateService.getTotalWithdrawalToday(account.getId());
        account.withdraw(Amount.of(command.amount()), command.description(), totalWithdrawalToday);
        accountRepository.update(account);

        return BalanceResponse.from(account);
    }
}
