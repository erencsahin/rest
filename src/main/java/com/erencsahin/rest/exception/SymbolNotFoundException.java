package com.erencsahin.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SymbolNotFoundException extends RuntimeException {
    public SymbolNotFoundException(String message) {
        super(message);
    }
}
