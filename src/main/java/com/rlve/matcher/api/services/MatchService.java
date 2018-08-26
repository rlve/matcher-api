package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.User;
import com.rlve.matcher.api.repositories.DetailsRepository;
import com.rlve.matcher.api.repositories.MatchRepository;
import com.rlve.matcher.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final DetailsRepository detailsRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, UserRepository userRepository, DetailsRepository detailsRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
    }

    @Transactional(readOnly = true)
    public Match findByPlace(String place) {
        Match result = matchRepository.findByPlace(place);
        return result;
    }

    public void AddData(){
        ZoneId zoneId = ZoneId.of("UTC+1");
        ZonedDateTime time = ZonedDateTime.of(2018, 9, 9, 18, 30, 0, 0, zoneId);

        Match match1 = new Match( "Pszczelna", time.plusDays(3).toInstant(),14);
        Match match2 = new Match( "Strakowa", time.plusDays(3).plusHours(1).toInstant(),20);
        Match match3 = new Match( "Bronowianka", time.plusDays(3).minusHours(2).toInstant(),10);


//
//        matchRepository.save(match1);
//        matchRepository.save(match2);
//        matchRepository.save(match3);

        User user1 = new User("Jack");
        User user2 = new User("Adam");
        User user3 = new User("Mike");


//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);

        Details details1 = new Details(match1, user1);
        Details details2 = new Details(match1, user2);
        Details details3 = new Details(match1, user3);

        Details details4 = new Details(match2, user1);
        Details details5 = new Details(match2, user2);
        Details details6 = new Details(match3, user3);

        detailsRepository.save(details1);
        detailsRepository.save(details2);
        detailsRepository.save(details3);
        detailsRepository.save(details4);
        detailsRepository.save(details5);
        detailsRepository.save(details6);

    }


}
