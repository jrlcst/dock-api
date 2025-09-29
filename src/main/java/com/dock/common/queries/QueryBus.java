package com.dock.common.queries;

import com.dock.common.application.GenericBus;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QueryBus extends GenericBus<Query<?>, QueryHandler<Query<?>, ?>, Object> {

    @SuppressWarnings("unchecked")
    public <R> R dispatch(Query<R> query) {
        return (R) dispatchInternal(query, (Class) QueryHandler.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object invokeHandle(QueryHandler<Query<?>, ?> handler, Query<?> message) {
        return handler.handle(message);
    }
}