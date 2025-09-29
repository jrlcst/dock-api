package com.dock.account.application.commands;

import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.commands.handler.BlockAccountHandler;
import com.dock.common.annotations.Handler;
import com.dock.common.commands.Command;

@Handler(BlockAccountHandler.class)
public record BlockAccountCommand(String accountId) implements Command<AccountResponse> {
}
