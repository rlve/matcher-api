package com.rlve.matcher.api.repositories;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.MatchSimple;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource(excerptProjection = MatchSimple.class, collectionResourceRel = "matches", path = "matches")
public interface MatchRepository extends Neo4jRepository<Match, Long> {
    Set<Match> findAllByPlace(@Param("place") String place);
}
