package com.rlve.matcher.api.repositories;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "details", path = "details")
public interface DetailsRepository extends Neo4jRepository<Details, Long> {

    <S extends Details> S save(S entity);
}
