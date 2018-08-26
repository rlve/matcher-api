package com.rlve.matcher.api.repositories;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends Neo4jRepository<User, Long> {

    <S extends User> S save(S entity);
}
