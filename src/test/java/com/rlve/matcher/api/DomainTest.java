package com.rlve.matcher.api;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.MatchState;
import com.rlve.matcher.api.domain.User;
import com.rlve.matcher.api.repositories.DetailsRepository;
import com.rlve.matcher.api.repositories.MatchRepository;
import com.rlve.matcher.api.repositories.UserRepository;
import com.rlve.matcher.api.services.SignService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataNeo4jTest
public class DomainTest {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DetailsRepository detailsRepository;

    private static final String NAME = "John Test";
    private static final String PLACE = "Test Place";
    private static final Instant matchDate =
            LocalDateTime.of(2018, 11, 15, 18, 0)
            .atZone(ZoneId.systemDefault())
            .toInstant();;

    @Test
    public void shouldAllowUserCreation() {
        User user = new User(NAME);
        user = userRepository.save(user);

        User userFromDb = userRepository.findById(user.getId()).orElseThrow();
        assertEquals(user.getId(), userFromDb.getId());
        assertEquals(user.getName(), userFromDb.getName());
    }

    @Test
    public void shouldAllowMatchCreation() {
        Match match = new Match(PLACE, matchDate, 20);
        match = matchRepository.save(match);

        Match matchFromDb = matchRepository.findById(match.getId()).orElseThrow();
        assertEquals(match.getId(), matchFromDb.getId());
        assertEquals(match.getPlace(), matchFromDb.getPlace());
        assertEquals(match.getMatchDate(), matchFromDb.getMatchDate());
        assertEquals(match.getMaxPlayers(), matchFromDb.getMaxPlayers());
    }

    @Test
    public void shouldAllowDetailsCreation() {
        User user = new User(NAME);
        Match match = new Match(PLACE, matchDate, 20);

        Details details = new Details(match, user);
        details = detailsRepository.save(details);

        Details detailsFromDb = detailsRepository.findByUserIdAndMatchId(user.getId(), match.getId()).orElseThrow();
        assertEquals(details.getId(), detailsFromDb.getId());
    }

    @Test
    public void shouldAllowDetailsToCreateMatchAndUser() {
        User user = new User(NAME);
        Match match = new Match(PLACE, matchDate, 20);

        Details details = new Details(match, user);
        details = detailsRepository.save(details);

        Set<User> usersFromDb = userRepository.findAllByNameLike(NAME);
        assertEquals(usersFromDb.size(), 1);
        assertEquals(user.getName(), usersFromDb.iterator().next().getName());

        Set<Match> matchesFromDb = matchRepository.findAllByPlace(PLACE);
        assertEquals(matchesFromDb.size(), 1);
        assertEquals(match.getPlace(), matchesFromDb.iterator().next().getPlace());
        assertEquals(match.getMatchDate(), matchesFromDb.iterator().next().getMatchDate());
    }


}