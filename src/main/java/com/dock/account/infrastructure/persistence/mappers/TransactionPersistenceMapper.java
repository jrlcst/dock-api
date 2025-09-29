package com.dock.account.infrastructure.persistence.mappers;

import com.dock.account.domain.models.Transaction;
import com.dock.account.domain.enumerations.TransactionType;
import com.dock.account.domain.vos.Amount;
import com.dock.account.infrastructure.persistence.entities.TransactionJpaEntity;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TransactionPersistenceMapper {

    public static Transaction toDomain(TransactionJpaEntity jpa) {
        return new Transaction(
                jpa.getId(),
                UUID.fromString(jpa.getUuid()),
                TransactionType.valueOf(jpa.getType()),
                Amount.of(jpa.getAmount()),
                jpa.getOccurredAt(),
                jpa.getDescription()
        );
    }

    public static TransactionJpaEntity fromDomain(String accountUuid, Transaction tx) {
        return TransactionJpaEntity.builder()
                .id(tx.persistenceId())
                .uuid(tx.id().toString())
                .accountUuid(accountUuid)
                .type(tx.type().name())
                .amount(tx.amount().value())
                .occurredAt(tx.occurredAt())
                .description(tx.description())
                .build();
    }
}
