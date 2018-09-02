package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.repositories.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DetailsService {
    private final DetailsRepository detailsRepository;

    @Autowired
    public DetailsService(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @Transactional
    public Optional<Details> findByUserIdAndMatchId(Long userId, Long matchId) {
        return detailsRepository.findByUserIdAndMatchId(userId, matchId);
    }

    @Transactional
    public void save(Details details){
        detailsRepository.save(details);
    }

}
