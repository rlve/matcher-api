package com.rlve.matcher.api.details;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data public class Details {
    private UUID id;
    private UUID matchId;
    private UUID userId;
    private ZonedDateTime addingDate;

    private Boolean userPresent;
    private Boolean userPaid;

    protected Details() {

    }

    public Details(UUID id, UUID matchId, UUID userId) {
        this.id = id;
        this.matchId = matchId;
        this.userId = userId;
        this.addingDate = ZonedDateTime.now();
    }

}
