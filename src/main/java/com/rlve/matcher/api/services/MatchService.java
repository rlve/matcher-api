package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class MatchService {
    private final MatchRepository matchRepository;


    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public Set<Match> findAllByPlace(String place) {
        return matchRepository.findAllByPlace(place);
    }

    @Transactional
    public Match findById(Long id) {
        return matchRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Match save(Match match){
        return matchRepository.save(match);
    }

}
