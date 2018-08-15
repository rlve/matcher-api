package com.rlve.matcher.api.user;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(UUID.fromString("7039d273-8e53-4345-b218-d5058b7edd70"), "Adammmm"));
        users.add(new User(UUID.fromString("2054d7f8-8b6d-4b85-aae8-e8f8301e4187"), "Eve"));
        users.add(new User(UUID.fromString("36a88684-b377-41fd-8957-f8db3d91602a"), "Jack"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) user.setId(UUID.randomUUID());
        user.setAddingDate(new Date());
        users.add(user);
        return user;
    }

    public User findOne(UUID id) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }

    public User deleteById(UUID id) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId().equals(id)) {
                iterator.remove();
                return user;
            }
        }

        return null;

    }

    public void updateById(UUID id, User updatedUser) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId().equals(id)) {
                user.setName(updatedUser.getName());
            }
        }
    }
}
