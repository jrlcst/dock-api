package com.dock.account.application.commands.handler;

import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.commands.UnblockAccountCommand;
import com.dock.account.domain.vos.AccountId;
import com.dock.account.domain.repositories.AccountRepository;
import com.dock.common.commands.CommandHandler;
import com.dock.common.exceptions.DomainException;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@Unremovable
@RequiredArgsConstructor
public class UnblockAccountHandler implements CommandHandler<UnblockAccountCommand, AccountResponse> {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountResponse handle(UnblockAccountCommand command) {
        var account = accountRepository.findById(AccountId.from(command.accountId()))
                .orElseThrow(() -> new DomainException("Conta não encontrada"));
        account.unblock();
        accountRepository.update(account);
        return AccountResponse.from(account);
    }
}
