package com.rlve.matcher.api.user;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("Adammmm"));
        users.add(new User("Eve"));
        users.add(new User("Jack"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

    public User findOne(UUID id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }

        return null;
    }

    public User deleteById(UUID id) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;

    }
}
