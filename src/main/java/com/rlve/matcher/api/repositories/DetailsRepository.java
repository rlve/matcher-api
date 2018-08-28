package com.rlve.matcher.api.repositories;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "details", path = "details")
public interface DetailsRepository extends Neo4jRepository<Details, Long> {

    @Query("MATCH (u:User)-[d]->(m:Match) WHERE id(u)={0} and id(m)={1} RETURN d")
    Optional<Details> findByUserIdAndMatchId(Long userId, Long matchId);

}
