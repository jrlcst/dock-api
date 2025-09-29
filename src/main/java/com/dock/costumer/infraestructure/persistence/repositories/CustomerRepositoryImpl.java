package com.dock.costumer.infraestructure.persistence.repositories;

import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.vos.CustomerId;
import com.dock.costumer.domain.vos.Document;
import com.dock.costumer.domain.repositories.CustomerRepository;
import com.dock.costumer.infraestructure.persistence.entities.CostumerJpaEntity;
import com.dock.costumer.infraestructure.persistence.mappers.CostumerPersistenceMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository, PanacheRepository<CostumerJpaEntity> {

    @Override
    public Optional<Customer> findById(CustomerId id) {

        final Optional<CostumerJpaEntity> optJpaEntity = find("uuid = :id", id.getValueString())
                .singleResultOptional();

        return optJpaEntity.map(CostumerPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByDocument(Document document) {
        final Optional<CostumerJpaEntity> optJpaEntity = find(
                "document = :document and deletedAt is null",
                Parameters.with("document", document.getValue())
        ).singleResultOptional();

        return optJpaEntity.map(CostumerPersistenceMapper::toDomain);
    }

    @Override
    public void save(Customer customer) {
        persist(CostumerPersistenceMapper.fromDomain(customer));
    }

    @Override
    public void delete(Customer customer) {

        final var params = Parameters.with("deletedAt", customer.getDeletedAt())
                .and("uuid", customer.getId().getValueString());

        update("deletedAt = :deletedAt where uuid = :uuid", params);
    }
}
