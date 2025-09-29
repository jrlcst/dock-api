package com.dock.costumer.application.commands.handler;

import com.dock.costumer.application.commands.CreateCustomerCommand;
import com.dock.costumer.application.api.response.CustomerResponse;
import com.dock.common.commands.CommandHandler;
import com.dock.costumer.domain.vos.Document;
import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.repositories.CustomerRepository;
import com.dock.costumer.domain.services.ValidateUniqueDocumentService;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Unremovable
@RequiredArgsConstructor
public class CreateCustomerHandler implements CommandHandler<CreateCustomerCommand, CustomerResponse> {

    private final CustomerRepository customerRepository;
    private final ValidateUniqueDocumentService validateUniqueDocumentService;

    @Override
    @Transactional
    public CustomerResponse handle(CreateCustomerCommand command) {
        log.info("Creating a costumer with name: {}", command.fullName());

        final Document document = Document.from(command.document());

        validateUniqueDocumentService.validate(document);

        final Customer customer = Customer.create(command.fullName(), document);
        customerRepository.save(customer);

        return CustomerResponse.from(customer);
    }
}
