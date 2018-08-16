package com.rlve.matcher.api.match;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class MatchDaoService {
    private static List<Match> matches = new ArrayList<>();

    static {
        ZoneId zoneId = ZoneId.of("UTC+1");
        matches.add(new Match(UUID.fromString("2473d25f-eafe-49c5-b966-d834c6adbdbe"), "Pszczelna",
                ZonedDateTime.of(2018, 9, 9, 18, 30, 0, 0, zoneId)));
        matches.add(new Match(UUID.fromString("642fd0a5-866f-4f94-9cce-9e5ddde6451a"), "Strakowa",
                ZonedDateTime.of(2018, 9, 15, 20, 0, 0, 0, zoneId)));
        matches.add(new Match(UUID.fromString("d395d65d-a301-4856-a592-d42c6c4cce72"), "Strakowa",
                ZonedDateTime.of(2018, 9, 20, 21, 30, 0, 0, zoneId)));
    }

    public List<Match> findAll() {
        return matches;
    }

    public Match save(Match match) {
        if (match.getId() == null) match.setId(UUID.randomUUID());
        match.setAddingDate(ZonedDateTime.now());

        matches.add(match);
        return match;
    }

    public Match findOne(UUID id) {
        Iterator<Match> iterator = matches.iterator();

        while (iterator.hasNext()) {
            Match match = iterator.next();

            if (match.getId().equals(id)) {
                return match;
            }
        }

        return null;
    }

    public Match deleteById(UUID id) {
        Iterator<Match> iterator = matches.iterator();

        while (iterator.hasNext()) {
            Match match = iterator.next();

            if (match.getId().equals(id)) {
                iterator.remove();
                return match;
            }
        }

        return null;

    }

    public void updateById(UUID id, Match updatedMatch) {
        Iterator<Match> iterator = matches.iterator();

        while (iterator.hasNext()) {
            Match match = iterator.next();

            if (match.getId().equals(id)) {
                if (updatedMatch.getPlace() != null)
                    match.setPlace(updatedMatch.getPlace());
                if (updatedMatch.getMatchDate() != null)
                    match.setMatchDate(updatedMatch.getMatchDate());
            }
        }
    }
}