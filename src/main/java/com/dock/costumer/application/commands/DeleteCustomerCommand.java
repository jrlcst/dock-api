package com.dock.costumer.application.commands;

import com.dock.common.annotations.Handler;
import com.dock.common.commands.Command;
import com.dock.costumer.application.commands.handler.DeleteCustomerHandler;

@Handler(DeleteCustomerHandler.class)
public record DeleteCustomerCommand(String document) implements Command<Void> {
}
