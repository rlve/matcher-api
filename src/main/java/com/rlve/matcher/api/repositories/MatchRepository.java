package com.rlve.matcher.api.repositories;

import com.rlve.matcher.api.domain.Match;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "matches", path = "matches")
public interface MatchRepository extends Neo4jRepository<Match, Long> {
    Match findByPlace(@Param("place") String place);
}
