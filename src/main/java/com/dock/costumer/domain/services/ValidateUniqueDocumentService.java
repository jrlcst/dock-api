package com.dock.costumer.domain.services;

import com.dock.common.exceptions.DomainException;
import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.vos.Document;
import com.dock.costumer.domain.repositories.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ValidateUniqueDocumentService {

    private final CustomerRepository customerRepository;

    public void validate(Document document) {

        final Optional<Customer> customer = customerRepository.findByDocument(document);

        if (customer.isPresent() && !customer.get().isDeleted()) {
            log.info("Document received: {} already exists in database", document.getValue());
            throw new DomainException(
                    "Não foi possível processar sua solicitação. Entre em contato com o suporte para mais informações."
            );
        }
    }
}
