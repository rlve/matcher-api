package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.User;
import com.rlve.matcher.api.match.MatchStates;
import com.rlve.matcher.api.repositories.DetailsRepository;
import com.rlve.matcher.api.repositories.MatchRepository;
import com.rlve.matcher.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void sign() {
        Match match = this.findById(97L).orElseThrow();
        User user = userService.findById(105L).orElseThrow();

        Details details = new Details(match, user);


        switch (this.resolveSquad(match, user.getId())) {
            case OK:
                details.setInSquad(Boolean.TRUE);
                break;
            case OK_RESERVES:
                details.setInReserves(Boolean.TRUE);
                break;
            default:
                break;
        }


        detailsRepository.save(details);

    }

    public MatchStates.SIGN resolveSquad(Match match, Long userId) {
        MatchStates.SIGN status;

        if (match.getSquad().contains(userId)) {
            status = MatchStates.SIGN.IN_SQUAD;
        } else {
            if (match.getSquad().size() >= match.getMaxPlayers()) {
                if (match.getReserves().contains(userId)) {
                    status = MatchStates.SIGN.IN_RESERVES;
                } else {
                    match.getReserves().add(userId);
                    status = MatchStates.SIGN.OK_RESERVES;
                }
            } else {
                match.getSquad().add(userId);
                status = MatchStates.SIGN.OK;
            }
        }

        return status;
    }

//    public void AddData(){
//        ZoneId zoneId = ZoneId.of("UTC+1");
//        ZonedDateTime time = ZonedDateTime.of(2018, 9, 9, 18, 30, 0, 0, zoneId);
//
//        Match match1 = new Match( "Pszczelna", time.plusDays(3).toInstant(),14);
//        Match match2 = new Match( "Strakowa", time.plusDays(3).plusHours(1).toInstant(),20);
//        Match match3 = new Match( "Bronowianka", time.plusDays(3).minusHours(2).toInstant(),10);
//
//        User user1 = new User("Jack");
//        User user2 = new User("Adam");
//        User user3 = new User("Mike");
//
//        Details details1 = new Details(match1, user1);
//        Details details2 = new Details(match1, user2);
//        Details details3 = new Details(match1, user3);
//
//        Details details4 = new Details(match2, user1);
//        Details details5 = new Details(match2, user2);
//        Details details6 = new Details(match3, user3);
//
//        detailsRepository.save(details1);
//        detailsRepository.save(details2);
//        detailsRepository.save(details3);
//        detailsRepository.save(details4);
//        detailsRepository.save(details5);
//        detailsRepository.save(details6);
//    }


}
