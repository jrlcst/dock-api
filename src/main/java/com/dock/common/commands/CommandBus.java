package com.dock.common.commands;

import com.dock.common.application.GenericBus;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommandBus extends GenericBus<Command<?>, CommandHandler<Command<?>, ?>, Object> {

    @SuppressWarnings("unchecked")
    public <R> R dispatch(Command<R> command) {
        return (R) dispatchInternal(command, (Class) CommandHandler.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object invokeHandle(CommandHandler<Command<?>, ?> handler, Command<?> message) {
        return handler.handle(message);
    }
}