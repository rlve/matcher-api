package com.rlve.matcher.api.match;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SquadException extends RuntimeException {
    public SquadException(String message) {
        super("Error with signing to the squad. " + message);
    }
}
