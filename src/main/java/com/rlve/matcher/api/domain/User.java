package com.rlve.matcher.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NodeEntity
@Data public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min=3, message = "Name should have at least 3 characters.")
    private String name;

    private Instant addingDate;

    @JsonIgnoreProperties("user")
    @Relationship(type = "PLAYED_IN")
    private List<Match> matches = new ArrayList<>();
//    private List<Details> details = new ArrayList<>();

    protected User() {

    }

    public User(String name) {
        this.name = name;
        this.addingDate = Instant.now();
    }

}
