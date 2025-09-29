package com.dock.account.application.queries;

import com.dock.account.application.api.response.StatementResponse;
import com.dock.account.application.queries.handler.GetStatementHandler;
import com.dock.common.annotations.Handler;
import com.dock.common.queries.Query;

@Handler(GetStatementHandler.class)
public record GetStatementQuery(
        String accountId,
        String start,
        String end
) implements Query<StatementResponse> {}