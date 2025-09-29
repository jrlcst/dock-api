package com.dock.account.infrastructure.persistence.repositories;

import com.dock.account.domain.enumerations.AccountStatus;
import com.dock.account.domain.models.Account;
import com.dock.account.domain.vos.AccountId;
import com.dock.account.domain.repositories.AccountRepository;
import com.dock.account.infrastructure.persistence.entities.AccountJpaEntity;
import com.dock.account.infrastructure.persistence.mappers.AccountPersistenceMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository, PanacheRepository<AccountJpaEntity> {

    @Override
    public Optional<Account> findById(AccountId id) {
        return find("uuid = :uuid", Parameters.with("uuid", id.getValueString()))
                .singleResultOptional()
                .map(AccountPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Account> findByCustomerId(String costumerId) {
        return find("costumerId = :costumerId and status <> :closedStatus",
                Parameters.with("costumerId", costumerId).and("closedStatus", AccountStatus.CLOSED)
        ).singleResultOptional().map(AccountPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Account> findByAgencyAndNumberAndCostumerId(
            final String agency,
            final String number,
            final String customerId
    ) {

        final var params = Parameters
                .with("agency", agency)
                .and("number", number)
                .and("costumerId", customerId);

        return find(
                """
                        select ac
                        from AccountJpaEntity ac
                        join fetch ac.costumer c
                        where ac.agency = :agency
                        and ac.number = :number
                        and c.uuid = :costumerId
                        """, params
        ).singleResultOptional().map(AccountPersistenceMapper::toDomain);
    }

    @Override
    public BigDecimal getTotalWithdrawalForAccountToday(AccountId accountId) {
        final ZoneId zone = ZoneId.of("America/Sao_Paulo");
        final Instant start = LocalDate.now(zone).atStartOfDay(zone).toInstant();
        final Instant end = start.plus(1, ChronoUnit.DAYS);

        return find("""
                        select coalesce(sum(t.amount), 0)
                        from TransactionJpaEntity t
                        where t.accountUuid = :accountId
                          and t.type = :type
                          and t.occurredAt >= :start
                          and t.occurredAt <  :end
                        """,
                Parameters.with("accountId", accountId.getValueString())
                        .and("type", "DEBIT")
                        .and("start", start)
                        .and("end", end)
        ).project(BigDecimal.class).singleResult();
    }

    @Override
    @Transactional
    public void save(Account account) {
        persist(AccountPersistenceMapper.fromDomain(account));
    }

    @Override
    @Transactional
    public void update(Account account) {

        final Long persistId = find(
                "select id from AccountJpaEntity where uuid = :uuid",
                Parameters.with("uuid", account.getId().getValueString())
        ).project(Long.class).singleResult();

        final AccountJpaEntity jpaEntity = AccountPersistenceMapper.fromDomain(account);
        getEntityManager().merge(jpaEntity.toBuilder().id(persistId).build());
    }
}
