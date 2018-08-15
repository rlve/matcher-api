package com.rlve.matcher.api.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class MatchController {
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
