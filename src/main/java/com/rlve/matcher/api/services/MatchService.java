package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.User;
import com.rlve.matcher.api.domain.MatchState;
import com.rlve.matcher.api.exceptions.UserNotFoundException;
import com.rlve.matcher.api.repositories.DetailsRepository;
import com.rlve.matcher.api.repositories.MatchRepository;
import com.rlve.matcher.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final DetailsRepository detailsRepository;

    private final UserService userService;

    @Autowired
    public MatchService(MatchRepository matchRepository, UserRepository userRepository, DetailsRepository detailsRepository, UserService userService) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Match findByPlace(String place) {
        Match result = matchRepository.findByPlace(place);
        return result;
    }

    @Transactional
    public Optional<Match> findById(Long id) {
        return matchRepository.findById(id);
    }


    public void signUserToMatch(Long userId, Long matchId) {
        User user = userService.findById(userId).orElseThrow();
        Match match = this.findById(matchId).orElseThrow();

        Details details = detailsRepository.findByUserIdAndMatchId(user.getId(), match.getId())
                                            .orElse(new Details(match, user));

        MatchState status = new MatchState();

        if (match.getSquad().contains(userId)) {
            status.setSignState(MatchState.SIGN.IN_SQUAD);
        } else {
            if (match.getSquad().size() >= match.getMaxPlayers()) {
                if (match.getReserves().contains(userId)) {
                    status.setSignState(MatchState.SIGN.IN_RESERVES);
                } else {
                    status.setSignState(MatchState.SIGN.OK_RESERVES);
                    details.setInReserves(Boolean.TRUE);
                    match.getReserves().add(userId);
                }
            } else {
                status.setSignState(MatchState.SIGN.OK);
                details.setInSquad(Boolean.TRUE);
                match.getSquad().add(userId);
            }
        }

        if (details.getId() == null)
            detailsRepository.save(details);
    }

    public void removeUserFromMatch(Long userId, Long matchId) {
        User user = userService.findById(userId).orElseThrow();
        Match match = this.findById(matchId).orElseThrow();

        Optional<Details> details = detailsRepository.findByUserIdAndMatchId(user.getId(), match.getId());

        if (details.isPresent()) {
            match.getDetails().remove(details.get());

            if (details.get().getInSquad()) {
                match.getSquad().remove(userId);

                if (match.getSquad().size() < match.getMaxPlayers()) {
                    if (match.getReserves().size() > 0) {
                        Long fromReserves = match.getReserves().iterator().next();
                        match.getSquad().add(fromReserves);
                        match.getReserves().remove(fromReserves);
                    }
                }
            } else {
                    match.getReserves().remove(userId);
            }

            matchRepository.save(match);
        }

    }

//    public void AddData(){
//        ZoneId zoneId = ZoneId.of("UTC+1");
//        ZonedDateTime time = ZonedDateTime.of(2018, 9, 9, 18, 30, 0, 0, zoneId);
//
//        Match match1 = new Match( "Pszczelna", time.plusDays(3).toInstant(),14);

//
//        User user1 = new User("Jack");

//
//        Details details1 = new Details(match1, user1);

//
//        detailsRepository.save(details1);

//    }


}
