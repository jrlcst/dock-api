package com.dock.account.infrastructure.persistence.entities;

import com.dock.account.domain.enumerations.AccountStatus;
import com.dock.costumer.infraestructure.persistence.constants.PersistenceConstants;
import com.dock.costumer.infraestructure.persistence.entities.CostumerJpaEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account", schema = PersistenceConstants.DOCK_SCHEMA)
public class AccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "costumer_id")
    private String costumerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "costumer_id",
            referencedColumnName = "uuid",
            updatable = false,
            insertable = false
    )
    private CostumerJpaEntity costumer;

    @Column(name = "agency", nullable = false, length = 4)
    private String agency;

    @Column(name = "number", nullable = false, length = 12)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private AccountStatus status;

    @Column(name = "balance", nullable = false, precision = 14, scale = 2)
    private BigDecimal balance;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "blocked_at")
    private Instant blockedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Column(name = "last_withdrawal_day")
    private Instant lastWithdrawalDay;

    @Column(name = "withdrawn_today", precision = 14, scale = 2)
    private BigDecimal withdrawnToday;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "uuid")
    private List<TransactionJpaEntity> transactions;
}
