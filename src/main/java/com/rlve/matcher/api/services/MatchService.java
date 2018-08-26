package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.repositories.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public Match findByTitle(String place) {
        Match result = matchRepository.findByPlace(place);
        return result;
    }

}
