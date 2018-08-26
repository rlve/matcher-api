package com.rlve.matcher.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

public class UserController {
    @Autowired
    private UserDaoService service;

    @GetMapping("/")
    public String welcome() {
        return "Welcome in matcher-api.";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable UUID id) {
        User user = service.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id: " + id);

        return user;
    }


    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User createdUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable UUID id, @Valid @RequestBody User updatedUser) {
        User user = service.findOne(id);
        service.updateById(id, updatedUser);

        if (user == null)
            throw new UserNotFoundException("id: " + id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
        User user = service.deleteById(id);

        if (user == null)
            throw new UserNotFoundException("id: " + id);
    }

}
