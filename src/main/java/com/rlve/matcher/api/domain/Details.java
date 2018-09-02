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

    private Boolean inSquad = Boolean.FALSE;
    private Boolean inReserves = Boolean.FALSE;
    private Boolean userPresent = Boolean.TRUE;
    private Boolean userPaid;

    public Details() {

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

    public Boolean getInSquad() {
        return inSquad;
    }

    public Boolean getInReserves() {
        return inReserves;
    }

    public void setInSquad(Boolean inSquad) {
        this.inSquad = inSquad;
    }

    public void setInReserves(Boolean inReserves) {
        this.inReserves = inReserves;
    }

    public void setUserPresent(Boolean userPresent) {
        this.userPresent = userPresent;
    }

    public void setUserPaid(Boolean userPaid) {
        this.userPaid = userPaid;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
