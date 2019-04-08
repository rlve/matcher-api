package com.rlve.matcher.api.domain;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;
import java.util.Set;

@Projection(name = "SimpleMatch", types = { Match.class })
public interface MatchSimple {
     String getPlace();

     Instant getMatchDate();

     Integer getMaxPlayers();

     Integer getMinPlayers();

     Integer getCost();
}
