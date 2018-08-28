package com.rlve.matcher.api.domain;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class DetailsEnriched {
    private UUID detailsId;
    private UUID matchId;
    private UUID userId;

    private Boolean userPresent;
    private Boolean userPaid;

    private String userName;
    private String matchPlace;
    private ZonedDateTime matchDate;

    public DetailsEnriched() {

    }

    public DetailsEnriched(UUID id, UUID matchId, UUID userId) {
        this.detailsId = id;
        this.matchId = matchId;
        this.userId = userId;
    }

}
