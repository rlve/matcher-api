package com.rlve.matcher.api.match;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data public class Match {
    private UUID id;
    private ZonedDateTime addingDate;

    @Size(min=3, message = "Place name should have at least 3 characters.")
    private String place;

    @Future(message = "Match date must be in the future.")
    private ZonedDateTime matchDate;

    private Integer maxPlayers;
    private Integer minPlayers;
    private Integer cost;

    protected Match() {

    }

    public Match(UUID id, String place, ZonedDateTime matchDate) {
        this.id = id;
        this.place = place;
        this.matchDate = matchDate;
        this.addingDate = ZonedDateTime.now();
    }

}
