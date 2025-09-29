package com.dock.costumer.domain.models;

import com.dock.common.exceptions.DomainException;
import com.dock.costumer.domain.vos.CustomerId;
import com.dock.costumer.domain.vos.Document;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
public final class Customer {
    private final CustomerId id;
    private final String fullName;
    private final Document document;
    private final Instant createdAt;
    private Instant deletedAt;

    private Customer(CustomerId id, String fullName, Document document, Instant createdAt, Instant deletedAt) {
        this.id = Objects.requireNonNull(id);
        this.fullName = validateName(fullName);
        this.document = Objects.requireNonNull(document);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.deletedAt = deletedAt;
    }

    public static Customer create(String fullName,
                                  Document document
    ) {
        return new Customer(CustomerId.newId(), fullName, document, Instant.now(), null);
    }

    public static Customer from(String id,
                                String fullName,
                                String document,
                                Instant createdAt,
                                Instant deletedAt
    ) {
        return new Customer(CustomerId.from(id), fullName, Document.from(document), createdAt, deletedAt);
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainException("Nome completo é obrigatório");
        }
        if (name.length() > 255) {
            throw new DomainException("Nome completo muito longo");
        }
        return name.trim();
    }

    public void markDeleted() {
        this.deletedAt = Instant.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}
