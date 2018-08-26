package com.rlve.matcher.api.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

public class old_MatchController {
    @Autowired
    private MatchDaoService service;

    @GetMapping("/matches")
    public List<Match> getAllMatches() {
        return service.findAll();
    }

    @GetMapping("/matches/{id}")
    public Match getMatch(@PathVariable UUID id) {
        Match match = service.findOne(id);

        if (match == null)
            throw new MatchNotFoundException("id: " + id);

        return match;
    }

    @PostMapping("/matches")
    public ResponseEntity<Object> addMatch(@Valid @RequestBody Match match) {
        Match createdMatch = service.save(match);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMatch.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/matches/{id}/sign/{userId}")
    public ResponseEntity<Object> signUser(@PathVariable UUID id, @PathVariable UUID userId) {
        Match match = service.findOne(id);

        if (match == null)
            throw new MatchNotFoundException("id: " + id);

        MatchStates.SIGN result = service.signUser(id, userId);
        String message = MatchStates.getMessage(result);

        if (result == MatchStates.SIGN.IN_RESERVES || result == MatchStates.SIGN.IN_SQUAD ) {
            throw new SquadException(String.format("%s id: %s", message, id));
        }

        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/matches/{id}/remove/{userId}")
    public ResponseEntity<Object> removeUser(@PathVariable UUID id, @PathVariable UUID userId) {
        Match match = service.findOne(id);

        if (match == null)
            throw new MatchNotFoundException("id: " + id);

        MatchStates.SIGN result = service.removeUserFromMatch(id, userId);
        String message = MatchStates.getMessage(result);

        if (result == MatchStates.SIGN.NO_USER) {
            throw new SquadException(String.format("%s id: %s", message, id));
        }

        return ResponseEntity.ok().body(message);
    }

    @PutMapping("/matches/{id}")
    public void updateMatch(@PathVariable UUID id, @Valid @RequestBody Match updatedMatch) {
        Match match = service.findOne(id);
        service.updateById(id, updatedMatch);

        if (match == null)
            throw new MatchNotFoundException("id: " + id);
    }

    @DeleteMapping("/matches/{id}")
    public void deleteMatch(@PathVariable UUID id) {
        Match match = service.deleteById(id);

        if (match == null)
            throw new MatchNotFoundException("id: " + id);
    }

}
