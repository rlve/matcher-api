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
    public List<DetailsEnriched> getAllDetailsByMatchId(@PathVariable UUID matchId) {
        return service.findAllByMatchId(matchId);
    }

    @GetMapping("/users/{userId}/details")
    public List<DetailsEnriched> getAllDetailsByUserId(@PathVariable UUID userId) {
        return service.findAllByUserId(userId);
    }

    @GetMapping("/details/{id}")
    public DetailsEnriched getOneDetails(@PathVariable UUID id) {
        DetailsEnriched detailsEnriched = service.findOne(id);

        if (detailsEnriched == null)
            throw new DetailsNotFoundException("id: " + id);

        return detailsEnriched;
    }

    @PutMapping("/details/{id}")
    public void updateDetails(@PathVariable UUID id, @Valid @RequestBody Details updatedDetails) {
        DetailsEnriched detailsEnriched = service.findOne(id);

        if (detailsEnriched == null)
            throw new DetailsNotFoundException("id: " + id);

        service.updateById(id, updatedDetails);
    }

}
