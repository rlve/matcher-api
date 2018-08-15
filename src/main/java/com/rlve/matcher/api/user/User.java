package com.rlve.matcher.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data public class User {
    private UUID id;

    @Size(min=3, message = "Name should have at least 3 characters.")
    private String name;

//    @Past(message = "Birth date must be in the past.")
    private Date addingDate;

    protected User() {

    }

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.addingDate = new Date();
    }

}
