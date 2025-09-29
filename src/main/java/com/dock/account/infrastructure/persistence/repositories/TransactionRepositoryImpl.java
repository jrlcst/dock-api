package com.dock.account.infrastructure.persistence.repositories;

import com.dock.account.infrastructure.persistence.entities.TransactionJpaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionRepositoryImpl implements PanacheRepository<TransactionJpaEntity> {
}
