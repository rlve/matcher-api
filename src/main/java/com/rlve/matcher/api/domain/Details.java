package com.rlve.matcher.api.domain;

import org.neo4j.ogm.annotation.*;

import java.time.Instant;

@RelationshipEntity(type = "PLAYED_IN")
public class Details {

    @Id
    @GeneratedValue
    private Long id;

    @EndNode
    private Match match;

    @StartNode
    private User user;

    private Instant addingDate;

    private Boolean userPresent;
    private Boolean userPaid;

    protected Details() {

    }

    public Details(Match match, User user) {
        this.match = match;
        this.user = user;
        this.addingDate = Instant.now();

        this.match.getDetails().add(this);
        this.user.getDetails().add(this);
    }

    public Long getId() {
        return id;
    }

    public Match getMatch() {
        return match;
    }

    public User getUser() {
        return user;
    }

    public Instant getAddingDate() {
        return addingDate;
    }

    public Boolean getUserPresent() {
        return userPresent;
    }

    public Boolean getUserPaid() {
        return userPaid;
    }
}
