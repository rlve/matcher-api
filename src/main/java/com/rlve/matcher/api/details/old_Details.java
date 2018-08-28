package com.rlve.matcher.api.details;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data public class old_Details {
    private UUID id;
    private UUID matchId;
    private UUID userId;
    private ZonedDateTime addingDate;

    private Boolean userPresent;
    private Boolean userPaid;

    protected old_Details() {

    }

    public old_Details(UUID id, UUID matchId, UUID userId) {
        this.id = id;
        this.matchId = matchId;
        this.userId = userId;
        this.addingDate = ZonedDateTime.now();
    }

}
