package com.rlve.matcher.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SquadException extends RuntimeException {
    public SquadException(String message) {
        super("Error with signing to the squad. " + message);
    }
}
