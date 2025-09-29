package com.dock.common.ddd.specifiations;

import com.dock.common.ddd.events.DomainEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Aggregate {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<DomainEvent> events = new ArrayList<>();

    protected void addEvent(DomainEvent event) {this.events.add(event);}

    public List<DomainEvent> pullEvents() {
        return this.events;
    }
}
