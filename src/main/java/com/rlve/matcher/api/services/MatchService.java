package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {
    private final MatchRepository matchRepository;


    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public Match findByPlace(String place) {
        return matchRepository.findByPlace(place);
    }

    @Transactional
    public Match findById(Long id) {
        return matchRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(Match match){
        matchRepository.save(match);
    }

}
