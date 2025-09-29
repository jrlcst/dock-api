package com.dock.costumer.application.commands;

import com.dock.common.annotations.Handler;
import com.dock.common.commands.Command;
import com.dock.costumer.application.api.response.CustomerResponse;
import com.dock.costumer.application.commands.handler.CreateCustomerHandler;

@Handler(CreateCustomerHandler.class)
public record CreateCustomerCommand(String fullName, String document) implements Command<CustomerResponse> {
}
