package com.dock.account.domain.vos;

import java.time.Instant;
import java.util.Objects;

public record Period(Instant start, Instant end) {
    public Period {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("end must be after start");
        }
    }

    public boolean contains(Instant dateTime) {
        return (dateTime.equals(start) || dateTime.isAfter(start))
                && (dateTime.equals(end)   || dateTime.isBefore(end));
    }
}
