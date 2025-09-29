package com.dock.account.application.commands;

import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.commands.handler.UnblockAccountHandler;
import com.dock.common.annotations.Handler;
import com.dock.common.commands.Command;

@Handler(UnblockAccountHandler.class)
public record UnblockAccountCommand(String accountId) implements Command<AccountResponse> {
}
