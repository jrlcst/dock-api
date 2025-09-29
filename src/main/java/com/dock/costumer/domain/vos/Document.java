package com.dock.costumer.domain.vos;

import com.dock.common.exceptions.DomainException;
import com.dock.costumer.domain.enumeration.DocumentTypeEnum;
import com.dock.costumer.domain.specifications.CnpjValidSpecification;
import com.dock.costumer.domain.specifications.CpfValidSpecification;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class Document {

    private final String value;
    private final DocumentTypeEnum type;

    public static Document from(String raw) {
        return new Document(raw);
    }

    private Document(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new DomainException("Documento é obrigatório");
        }

        final String digits = value.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            throw new DomainException("Documento inválido");
        }

        final DocumentTypeEnum inferred = inferTypeFromDigits(digits);
        if (inferred == null) {
            throw new DomainException("Documento deve ter 11 (CPF) ou 14 (CNPJ) dígitos");
        }

        boolean valid = switch (inferred) {
            case CPF -> CpfValidSpecification.isSatisfiedBy(digits);
            case CNPJ -> CnpjValidSpecification.isSatisfiedBy(digits);
        };

        if (!valid) {
            throw new DomainException(inferred == DocumentTypeEnum.CPF ? "CPF inválido" : "CNPJ inválido");
        }

        this.value = digits;
        this.type = Objects.requireNonNull(inferred);
    }

    private static DocumentTypeEnum inferTypeFromDigits(String digits) {
        int len = digits.length();
        if (len == 11) return DocumentTypeEnum.CPF;
        if (len == 14) return DocumentTypeEnum.CNPJ;
        return null;
    }
}
