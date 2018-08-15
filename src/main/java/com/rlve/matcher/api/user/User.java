package com.rlve.matcher.api.user;

import lombok.Data;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data public class User {
    private UUID id;

    @Size(min=3, message = "Name should have at least 3 characters.")
    private String name;

    private ZonedDateTime addingDate;

    protected User() {

    }

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.addingDate = ZonedDateTime.now();
    }

}
