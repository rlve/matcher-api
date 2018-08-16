package com.rlve.matcher.api.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class DetailsController {
    @Autowired
    private DetailsDaoService service;

    @GetMapping("/matches/{matchId}/details")
    public List<Details> getAllDetailsByMatchId(@PathVariable UUID matchId) {
        return service.findAllByMatchId(matchId);
    }

    @GetMapping("/users/{userId}/details")
    public List<Details> getAllDetailsByUserId(@PathVariable UUID userId) {
        return service.findAllByUserId(userId);
    }

    @GetMapping("/details/{id}")
    public Details getOneDetails(@PathVariable UUID id) {
        Details details = service.findOne(id);

        if (details == null)
            throw new DetailsNotFoundException("id: " + id);

        return details;
    }

    @PutMapping("/details/{id}")
    public void updateDetails(@PathVariable UUID id, @Valid @RequestBody Details updatedDetails) {
        Details details = service.findOne(id);
        service.updateById(id, updatedDetails);

        if (details == null)
            throw new DetailsNotFoundException("id: " + id);
    }

}
