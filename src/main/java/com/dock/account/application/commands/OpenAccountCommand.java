package com.dock.account.application.commands;

import com.dock.common.annotations.Handler;
import com.dock.common.commands.Command;
import com.dock.account.application.api.response.AccountResponse;
import com.dock.account.application.commands.handler.OpenAccountHandler;

@Handler(OpenAccountHandler.class)
public record OpenAccountCommand(String customerDocument) implements Command<AccountResponse> {
}
