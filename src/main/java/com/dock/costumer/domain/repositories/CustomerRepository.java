package com.dock.costumer.domain.repositories;

import com.dock.costumer.domain.vos.Document;
import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.vos.CustomerId;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(CustomerId id);
    Optional<Customer> findByDocument(Document cpf);
    void save(Customer customer);
    void delete(Customer customer);
}
