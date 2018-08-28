package com.rlve.matcher.api.details;

import com.rlve.matcher.api.domain.DetailsEnriched;
import com.rlve.matcher.api.exceptions.DetailsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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
    public void updateDetails(@PathVariable UUID id, @Valid @RequestBody old_Details updatedDetails) {
        DetailsEnriched detailsEnriched = service.findOne(id);

        if (detailsEnriched == null)
            throw new DetailsNotFoundException("id: " + id);

        service.updateById(id, updatedDetails);
    }

}
