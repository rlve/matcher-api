package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.User;
import com.rlve.matcher.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Set<User> findAllByNameLike(String name) {
        return userRepository.findAllByNameLike(name);
    }

    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public User save(User User){
        return userRepository.save(User);
    }
}
