package com.rlve.matcher.api.domain;


import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data public class Match {

    @Id
    @GeneratedValue
    private Long id;
//    private ZonedDateTime addingDate;

    @Size(min=3, message = "Place name should have at least 3 characters.")
    private String place;

//    @Future(message = "Details date must be in the future.")
//    private ZonedDateTime matchDate;
//
//    private Integer maxPlayers = 15;
//    private Integer minPlayers = 10;
//    private Integer cost;

    @Relationship(type = "PLAYED_IN", direction = Relationship.INCOMING)
    private List<User> users = new ArrayList<>();
//
//    private ArrayList<UUID> squad = new ArrayList<UUID>();
//    private ArrayList<UUID> reserves = new ArrayList<UUID>();

    protected Match() {

    }

    public Match( String place, ZonedDateTime matchDate, Integer maxPlayers) {
        this.place = place;
//        this.matchDate = matchDate;
//        this.maxPlayers = maxPlayers;
//
//        this.addingDate = ZonedDateTime.now();

    }
}
