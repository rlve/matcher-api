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
        userRepository.save(user);

        Iterable<User> foundUsers = userRepository.findAll();
        Optional<User> createdUserFromDb =
                StreamSupport.stream(foundUsers.spliterator(), false)
                        .filter(user1 -> user1.getName().equals(name))
                        .findAny();

        Optional<User> expectedUser = userRepository.findById(createdUserFromDb.orElseThrow().getId());

        assertEquals(createdUserFromDb, expectedUser);
        assertEquals(createdUserFromDb.get().getName(), expectedUser.get().getName());
        assertEquals(createdUserFromDb.get().getId(), expectedUser.get().getId());
    }
}