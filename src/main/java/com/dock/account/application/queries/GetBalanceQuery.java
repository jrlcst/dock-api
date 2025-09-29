package com.dock.account.application.queries;

import com.dock.account.application.api.response.BalanceResponse;
import com.dock.account.application.queries.handler.GetBalanceHandler;
import com.dock.common.annotations.Handler;
import com.dock.common.queries.Query;

@Handler(GetBalanceHandler.class)
public record GetBalanceQuery(String accountId) implements Query<BalanceResponse> {
}
