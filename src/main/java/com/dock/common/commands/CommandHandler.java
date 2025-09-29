package com.dock.common.commands;

public interface CommandHandler<C extends Command<R>, R> {
    R handle(final C command);
}
