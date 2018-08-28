package com.rlve.matcher.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@NodeEntity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min=3, message = "Name should have at least 3 characters.")
    private String name;

    @CreatedDate
    private Instant addingDate;


    @JsonIgnoreProperties("user")
    @Relationship(type = "PLAYED_IN")
    private Set<Details> details = new HashSet<>();

    protected User() {
        this.addingDate = Instant.now();
    }

    public User(String name) {
        this.name = name;
        this.addingDate = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getAddingDate() {
        return addingDate;
    }

    public Set<Details> getDetails() {
        return details;
    }


}
