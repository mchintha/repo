package com.spring.reactive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestExceptionFlux extends ResponseStatusException {


    public BadRequestExceptionFlux(HttpStatus status) {
        super(status);
    }

    public BadRequestExceptionFlux(HttpStatus status, String reason) {
        super(status, reason);
    }

    public BadRequestExceptionFlux(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
