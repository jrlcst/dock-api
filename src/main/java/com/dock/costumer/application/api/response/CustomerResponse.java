package com.dock.costumer.application.api.response;

import com.dock.costumer.domain.enumeration.DocumentTypeEnum;
import com.dock.costumer.domain.models.Customer;
import com.dock.costumer.domain.vos.Document;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerResponse(UUID id, String fullName, DocumentResponse document) {

    @Builder
    public record DocumentResponse(String value, DocumentTypeEnum type) {

        public static DocumentResponse from(Document document) {
            return DocumentResponse.builder()
                    .value(document.getValue())
                    .type(document.getType())
                    .build();
        }

    }

    public static CustomerResponse from(Customer costumer) {
        return CustomerResponse.builder()
                .id(costumer.getId().value())
                .fullName(costumer.getFullName())
                .document(DocumentResponse.from(costumer.getDocument()))
                .build();
    }
}
