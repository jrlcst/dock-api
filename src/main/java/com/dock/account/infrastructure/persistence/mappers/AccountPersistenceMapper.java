package com.dock.account.infrastructure.persistence.mappers;

import com.dock.account.domain.models.*;
import com.dock.account.domain.vos.Amount;
import com.dock.account.domain.vos.AccountId;
import com.dock.account.domain.vos.AccountNumber;
import com.dock.account.domain.vos.AgencyNumber;
import com.dock.account.infrastructure.persistence.entities.AccountJpaEntity;
import com.dock.costumer.domain.vos.CustomerId;
import com.dock.costumer.infraestructure.persistence.mappers.CostumerPersistenceMapper;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class AccountPersistenceMapper {

    public static Account toDomain(final AccountJpaEntity jpaEntity) {
        return Account.from(
                new AccountId(UUID.fromString(jpaEntity.getUuid())),
                CostumerPersistenceMapper.toDomain(jpaEntity.getCostumer()),
                CustomerId.from(jpaEntity.getCostumerId()),
                new AccountNumber(jpaEntity.getNumber()),
                new AgencyNumber(jpaEntity.getAgency()),
                jpaEntity.getStatus(),
                Amount.of(jpaEntity.getBalance()),
                jpaEntity.getTransactions().stream().map(TransactionPersistenceMapper::toDomain).toList()
        );
    }

    public static AccountJpaEntity fromDomain(Account account) {
        return AccountJpaEntity.builder()
                .uuid(account.getId().getValueString())
                .costumerId(account.getCustomerId().getValueString())
                .agency(account.getAgency().value())
                .number(account.getNumber().value())
                .status(account.getStatus())
                .balance(account.getBalance().value())
                .createdAt(Instant.now())
                .transactions(account.getTransactions().stream()
                        .map(tx -> TransactionPersistenceMapper.fromDomain(account.getId().getValueString(), tx))
                        .toList())
                .build();
    }
}
