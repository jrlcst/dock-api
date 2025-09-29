package com.dock.account.application.queries;

import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.queries.handler.GetAccountDetailsHandler;
import com.dock.common.annotations.Handler;
import com.dock.common.queries.Query;

@Handler(GetAccountDetailsHandler.class)
public record GetAccountDetailsQuery(String number, String agency, String document) implements Query<AccountResponse> {
}
