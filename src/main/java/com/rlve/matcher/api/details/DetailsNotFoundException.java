package com.rlve.matcher.api.details;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DetailsNotFoundException extends RuntimeException {
    public DetailsNotFoundException(String message) {
        super("Details not found. " + message);
    }
}
