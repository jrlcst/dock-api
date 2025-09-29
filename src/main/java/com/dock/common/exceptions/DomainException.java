package com.dock.common.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        log.error(message);
        super(message);
    }
}
