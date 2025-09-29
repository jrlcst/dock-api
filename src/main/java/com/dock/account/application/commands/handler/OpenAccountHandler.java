package com.dock.account.application.commands.handler;

import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.commands.OpenAccountCommand;
import com.dock.account.domain.models.*;
import com.dock.account.domain.repositories.AccountRepository;
import com.dock.account.domain.services.AccountValidateService;
import com.dock.account.domain.vos.AccountNumber;
import com.dock.account.domain.vos.AgencyNumber;
import com.dock.common.commands.CommandHandler;
import com.dock.common.exceptions.DomainException;
import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.repositories.CustomerRepository;
import com.dock.costumer.domain.vos.Document;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
@ApplicationScoped
@Unremovable
@RequiredArgsConstructor
public class OpenAccountHandler implements CommandHandler<OpenAccountCommand, AccountResponse> {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountValidateService accountValidateService;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    @Transactional
    public AccountResponse handle(OpenAccountCommand command) {
        log.info("Opening account for customer document: {}", command.customerDocument());

        final Document document = Document.from(command.customerDocument());

        final Customer customer = customerRepository.findByDocument(document)
                .orElseThrow(() -> new DomainException("Portador não encontrado"));

        accountValidateService.validateExistsAccountToCustomer(customer.getId().getValueString());

        final AgencyNumber agency = new AgencyNumber(generateDigits(4));
        final AccountNumber number = new AccountNumber(generateDigits(8));

        final Account account = Account.open(customer, number, agency);
        accountRepository.save(account);

        return AccountResponse.from(account);
    }

    private String generateDigits(int size) {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}
