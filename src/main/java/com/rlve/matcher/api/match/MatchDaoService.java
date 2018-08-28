package com.rlve.matcher.api.match;

import com.rlve.matcher.api.domain.MatchState;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class MatchDaoService {
    private static List<Match> matches = new ArrayList<>();

    static {
        ZoneId zoneId = ZoneId.of("UTC+1");
        matches.add(new Match(UUID.fromString("2473d25f-eafe-49c5-b966-d834c6adbdbe"), "Pszczelna",
                ZonedDateTime.of(2018, 9, 9, 18, 30, 0, 0, zoneId), 14));
        matches.add(new Match(UUID.fromString("642fd0a5-866f-4f94-9cce-9e5ddde6451a"), "Strakowa",
                ZonedDateTime.of(2018, 9, 15, 20, 0, 0, 0, zoneId), 15));
        matches.add(new Match(UUID.fromString("d395d65d-a301-4856-a592-d42c6c4cce72"), "Strakowa",
                ZonedDateTime.of(2018, 9, 20, 21, 30, 0, 0, zoneId), 15));
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

    public String canSign(Match match, UUID userId) {
        String message = null;

        if (match.getSquad().contains(userId))
            message = "User already in squad.";

        if (match.getSquad().size() >= match.getMaxPlayers())
            if (match.getReserves().contains(userId))
                message = "User already in reserves.";

        return message;
    }

    public MatchState.SIGN signUser(UUID id, UUID userId) {
        Iterator<Match> iterator = matches.iterator();
        MatchState.SIGN status = MatchState.SIGN.OK;

        while (iterator.hasNext()) {
            Match match = iterator.next();

            if (match.getId().equals(id)) {

                if (match.getSquad().contains(userId)) {
                    status = MatchState.SIGN.IN_SQUAD;
                } else {
                    if (match.getSquad().size() >= match.getMaxPlayers()) {
                        if (match.getReserves().contains(userId)) {
                            status = MatchState.SIGN.IN_RESERVES;
                        } else {
                            match.getReserves().add(userId);
                            status = MatchState.SIGN.OK_RESERVES;
                        }
                    } else {
                        match.getSquad().add(userId);
                        status = MatchState.SIGN.OK;
                    }
                }
            }
        }

        return status;
    }

    public MatchState.SIGN removeUserFromMatch(UUID id, UUID userId) {
        Iterator<Match> iterator = matches.iterator();
        MatchState.SIGN status = MatchState.SIGN.OK_REMOVED;

        while (iterator.hasNext()) {
            Match match = iterator.next();

            if (match.getId().equals(id)) {

                if (match.getSquad().contains(userId)) {
                    match.getSquad().remove(userId);

                    if (match.getSquad().size() < match.getMaxPlayers()) {
                        if (match.getReserves().size() > 0) {
                            UUID fromReserves = match.getReserves().get(0);
                            match.getSquad().add(fromReserves);
                            match.getReserves().remove(fromReserves);
                        }
                    }
                } else {
                    if (match.getReserves().contains(userId)) {
                        match.getReserves().remove(id);
                    } else {
                        status = MatchState.SIGN.NO_USER;
                    }
                }
            }
        }

        return status;
    }
}
