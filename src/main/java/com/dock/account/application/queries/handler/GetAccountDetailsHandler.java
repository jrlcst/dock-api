package com.dock.account.application.queries.handler;

import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.queries.GetAccountDetailsQuery;
import com.dock.account.domain.models.Account;
import com.dock.account.domain.repositories.AccountRepository;
import com.dock.common.exceptions.DomainException;
import com.dock.common.queries.QueryHandler;
import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.repositories.CustomerRepository;
import com.dock.costumer.domain.vos.Document;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@Unremovable
@RequiredArgsConstructor
public class GetAccountDetailsHandler implements QueryHandler<GetAccountDetailsQuery, AccountResponse> {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public AccountResponse handle(GetAccountDetailsQuery command) {

        final Customer customer = customerRepository.findByDocument(Document.from(command.document()))
                .orElseThrow(() -> new DomainException("Portador não encontrado"));

        final Account account = accountRepository.findByAgencyAndNumberAndCostumerId(
                command.agency(),
                command.number(),
                customer.getId().getValueString()
        ).orElseThrow(() -> new DomainException("Conta não encontrada"));

        return AccountResponse.from(account);
    }
}
