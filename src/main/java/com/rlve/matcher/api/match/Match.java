package com.rlve.matcher.api.match;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Data public class Match {
    private UUID id;
    private ZonedDateTime addingDate;

    @Size(min=3, message = "Place name should have at least 3 characters.")
    private String place;

    @Future(message = "Details date must be in the future.")
    private ZonedDateTime matchDate;

    private Integer maxPlayers = 15;
    private Integer minPlayers = 10;
    private Integer cost;

    private ArrayList<UUID> squad = new ArrayList<UUID>();
    private ArrayList<UUID> reserves = new ArrayList<UUID>();

    protected Match() {

    }

    public Match(UUID id, String place, ZonedDateTime matchDate, Integer maxPlayers) {
        this.id = id;
        this.place = place;
        this.matchDate = matchDate;
        this.maxPlayers = maxPlayers;

        this.addingDate = ZonedDateTime.now();

    }
}
