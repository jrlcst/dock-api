package com.dock.account.domain.vos;

import com.dock.common.exceptions.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.util.Objects.isNull;

public record Amount(BigDecimal value) implements Comparable<Amount> {

    public static final Amount ZERO = new Amount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));

    public Amount {
        if (isNull(value)) {
            throw new DomainException("Valor nao pode ser nulo");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
        if (value.compareTo(new BigDecimal("0.00")) < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
    }

    public static Amount of(BigDecimal value) {
        if (isNull(value)) {
            value = BigDecimal.ZERO;
        }
        return new Amount(value);
    }

    public Amount add(Amount other) {
        return new Amount(this.value.add(other.value));
    }

    public Amount subtract(Amount other) {
        var result = this.value.subtract(other.value);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Resulting money cannot be negative");
        }
        return new Amount(result);
    }

    public boolean gte(Amount other) {
        return this.value.compareTo(other.value) >= 0;
    }

    public boolean gt(Amount other) {
        return this.value.compareTo(other.value) > 0;
    }

    public boolean lt(Amount other) {
        return this.value.compareTo(other.value) < 0;
    }

    @Override
    public int compareTo(Amount o) {
        return this.value.compareTo(o.value);
    }
}
