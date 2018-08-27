package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.User;
import com.rlve.matcher.api.repositories.DetailsRepository;
import com.rlve.matcher.api.repositories.MatchRepository;
import com.rlve.matcher.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final DetailsRepository detailsRepository;

    @Autowired
    public UserService(MatchRepository matchRepository, UserRepository userRepository, DetailsRepository detailsRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
    }

    @Transactional
    public Set<User> findAllByNameLike(String name) {
        Set<User> users = userRepository.findAllByNameLike(name);
        return users;
    }

    @Transactional
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
