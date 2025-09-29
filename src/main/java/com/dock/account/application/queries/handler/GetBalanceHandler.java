package com.dock.account.application.queries.handler;

import com.dock.account.application.api.response.BalanceResponse;
import com.dock.account.application.queries.GetBalanceQuery;
import com.dock.account.domain.vos.AccountId;
import com.dock.account.domain.repositories.AccountRepository;
import com.dock.common.exceptions.DomainException;
import com.dock.common.queries.QueryHandler;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@Unremovable
@RequiredArgsConstructor
public class GetBalanceHandler implements QueryHandler<GetBalanceQuery, BalanceResponse> {

    private final AccountRepository accountRepository;

    @Override
    public BalanceResponse handle(GetBalanceQuery command) {
        var account = accountRepository.findById(AccountId.from(command.accountId()))
                .orElseThrow(() -> new DomainException("Conta não encontrada"));
        return BalanceResponse.from(account);
    }
}
