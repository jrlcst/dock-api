package com.dock.account.application.commands;

import com.dock.account.application.api.response.BalanceResponse;
import com.dock.account.application.commands.handler.DepositHandler;
import com.dock.common.annotations.Handler;
import com.dock.common.commands.Command;

import java.math.BigDecimal;

@Handler(DepositHandler.class)
public record DepositCommand(String accountId, BigDecimal amount, String description) implements Command<BalanceResponse> {
}
