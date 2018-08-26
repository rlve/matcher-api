package com.rlve.matcher.api.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NodeEntity
@Data public class Match {

    @Id
    @GeneratedValue
    private Long id;
    private Instant addingDate;

    @Size(min=3, message = "Place name should have at least 3 characters.")
    private String place;

    @Future(message = "Details date must be in the future.")
    private Instant matchDate;

    private Integer maxPlayers = 15;
    private Integer minPlayers = 10;
    private Integer cost;

    @JsonIgnoreProperties("match")
    @Relationship(type = "PLAYED_IN", direction = Relationship.INCOMING)
    private List<Details> details = new ArrayList<>();

    private ArrayList<Long> squad = new ArrayList<>();
    private ArrayList<Long> reserves = new ArrayList<>();

    protected Match() {

    }

    public Match( String place, Instant matchDate, Integer maxPlayers) {
        this.place = place;
        this.matchDate = matchDate;
        this.maxPlayers = maxPlayers;
        this.addingDate = Instant.now();
    }

    public List<Details> getDetails() {
        return details;
    }

    public void addDetails(Details details) {

    }
}
