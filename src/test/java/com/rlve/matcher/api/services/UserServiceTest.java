package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.User;
import com.rlve.matcher.api.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataNeo4jTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllByNameLike() {
        String name = "TESTED";
        User user = new User(name);
        userRepository.save(user);

        Set<User> expectedUser = userRepository.findAllByNameLike(name);

        assertEquals(expectedUser.size(), 1);
        assertEquals(expectedUser.iterator().next().getName(), name);
    }

    @Test
    public void findById() {
        String name = "TESTED";
        User user = new User(name);
        user = userRepository.save(user);

        User userFromDb = userRepository.findById(user.getId()).orElseThrow();

        assertEquals(userFromDb, user);
        assertEquals(userFromDb.getName(), user.getName());
        assertEquals(userFromDb.getId(), user.getId());
    }


}