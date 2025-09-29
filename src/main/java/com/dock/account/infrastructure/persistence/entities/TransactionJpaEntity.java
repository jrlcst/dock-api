package com.dock.account.infrastructure.persistence.entities;

import com.dock.costumer.infraestructure.persistence.constants.PersistenceConstants;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction", schema = PersistenceConstants.DOCK_SCHEMA)
public class TransactionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "account_id", nullable = false, length = 36)
    private String accountUuid;

    @Column(name = "type", nullable = false, length = 16)
    private String type;

    @Column(name = "amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "description")
    private String description;
}
