package com.dock.costumer.infraestructure.persistence.mappers;

import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.infraestructure.persistence.entities.CostumerJpaEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class CostumerPersistenceMapper {

    public static Customer toDomain(CostumerJpaEntity jpaEntity) {
        return Customer.from(
                jpaEntity.getUuid(),
                jpaEntity.getFullName(),
                jpaEntity.getDocument(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getDeletedAt()
        );
    }

    public static CostumerJpaEntity fromDomain(Customer costumer) {
        return CostumerJpaEntity.builder()
                .uuid(costumer.getId().value().toString())
                .fullName(costumer.getFullName())
                .document(costumer.getDocument().getValue())
                .createdAt(costumer.getCreatedAt())
                .deletedAt(costumer.getDeletedAt())
                .build();
    }
}
