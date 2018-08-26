package com.rlve.matcher.api.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.time.ZonedDateTime;

@RelationshipEntity(type = "PLAYED_IN")
@Data public class Details {

    @Id
    @GeneratedValue
    private Long id;

    @EndNode
    private Match match;

    @StartNode
    private User user;

    private ZonedDateTime addingDate;

    private Boolean userPresent;
    private Boolean userPaid;

    protected Details() {

    }

    public Details(Match match, User user) {
        this.match = match;
        this.user = user;
    }
}
