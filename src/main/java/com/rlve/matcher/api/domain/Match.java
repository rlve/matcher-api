package com.rlve.matcher.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;

@NodeEntity
public class Match {

    @JsonProperty("id")
    @Id
    @GeneratedValue
    private Long id;
    @CreatedDate
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
    private Set<Details> details = new HashSet<>();

    private List<Long> squad = new ArrayList<>();
    private List<Long> reserves = new ArrayList<>();

    protected Match() {
        this.addingDate = Instant.now();
    }

    public Match( String place, Instant matchDate, Integer maxPlayers) {
        this.place = place;
        this.matchDate = matchDate;
        this.maxPlayers = maxPlayers;
        this.addingDate = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Instant getAddingDate() {
        return addingDate;
    }

    public String getPlace() {
        return place;
    }

    public Instant getMatchDate() {
        return matchDate;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public Integer getCost() {
        return cost;
    }

    public Set<Details> getDetails() {
        return details;
    }

    public List<Long> getSquad() {
        return squad;
    }

    public List<Long> getReserves() {
        return reserves;
    }
}
