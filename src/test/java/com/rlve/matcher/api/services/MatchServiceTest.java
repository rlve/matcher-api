package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.repositories.MatchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataNeo4jTest
public class MatchServiceTest {
    @Autowired
    private MatchRepository matchRepository;

    @Test
    public void findByPlace() {
        String testPlace = "TestPlace";
        Match match = new Match(testPlace, Instant.now(), 80);
        matchRepository.save(match);

        Match matchFromDb = matchRepository.findByPlace(testPlace);

        assertEquals(match, matchFromDb);
        assertEquals(match.getPlace(), matchFromDb.getPlace());
        assertEquals(match.getMatchDate(), matchFromDb.getMatchDate());
    }

    @Test
    public void findById() {
        String testPlace = "TestPlace";
        Match match = new Match(testPlace, Instant.now(), 80);
        matchRepository.save(match);

        Match matchFromDb = matchRepository.findByPlace(testPlace);

        Match expectedMatch = matchRepository.findById(matchFromDb.getId()).orElseThrow();

        assertEquals(match, expectedMatch);
        assertEquals(match.getPlace(), expectedMatch.getPlace());
        assertEquals(match.getId(), expectedMatch.getId());
    }


}