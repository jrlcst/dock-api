package com.dock.costumer.application.commands.handler;

import com.dock.account.domain.repositories.AccountRepository;
import com.dock.common.exceptions.DomainException;
import com.dock.costumer.application.commands.DeleteCustomerCommand;
import com.dock.common.commands.CommandHandler;
import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.vos.Document;
import com.dock.costumer.domain.repositories.CustomerRepository;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@ApplicationScoped
@Unremovable
@RequiredArgsConstructor
public class DeleteCustomerHandler implements CommandHandler<DeleteCustomerCommand, Void> {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Void handle(DeleteCustomerCommand command) {
        log.info("Deleting a costumer with document: {}", command.document());

        final Document document = Document.from(command.document());
        final Optional<Customer> customerOpt = customerRepository.findByDocument(document);

        if (customerOpt.isEmpty()) {
            throw new DomainException("Portador nao encontrado");
        }

        final Customer customer = customerOpt.get();

        if (accountRepository.findByCustomerId(customer.getId().getValueString()).isPresent()) {
            throw new DomainException("Existe uma conta vinculada a este portador.");
        }

        customer.markDeleted();
        customerRepository.delete(customer);

        return null;
    }
}
