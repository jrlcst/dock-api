package com.dock.account.application.commands;

import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.commands.handler.CloseAccountHandler;
import com.dock.common.annotations.Handler;
import com.dock.common.commands.Command;

@Handler(CloseAccountHandler.class)
public record CloseAccountCommand(String accountId) implements Command<AccountResponse> {
}
