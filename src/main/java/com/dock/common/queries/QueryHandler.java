package com.dock.common.queries;

public interface QueryHandler<C extends Query<R>, R> {
    R handle(C command);
}
