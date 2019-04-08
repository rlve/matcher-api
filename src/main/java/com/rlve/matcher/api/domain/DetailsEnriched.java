package com.rlve.matcher.api.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;

@Projection(name = "enrichedDetails", types = { Details.class })
public interface DetailsEnriched {

    @Value("#{target.match.place}")
    String getMatchPlace();

    @Value("#{target.match.matchDate}")
    Instant getMatchDate();

    @Value("#{target.match.cost}")
    Instant getMatchCost();

    @Value("#{target.user.name}")
    String getUserName();

    Boolean getInSquad();
    Boolean getInReserves();
    Boolean getUserPresent();
    Boolean getUserPaid();

}
