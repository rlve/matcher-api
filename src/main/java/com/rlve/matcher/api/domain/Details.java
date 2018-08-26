package com.rlve.matcher.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.time.Instant;

@RelationshipEntity(type = "PLAYED_IN")
@Data public class Details {

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

//        this.match.getDetails().add(this);
//        this.user.getMatches().add(match);
    }

    @Override
    public String toString() {
        return "Details{}";
    }
}
