package com.rlve.matcher.api.controllers;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.match.MatchNotFoundException;
import com.rlve.matcher.api.match.MatchStates;
import com.rlve.matcher.api.match.SquadException;
import com.rlve.matcher.api.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/custom")
    public void Custom(){
        matchService.sign();
    }

//    @PostMapping("/matches/{id}/sign/{userId}")
//    public ResponseEntity<Object> signUser(@PathVariable Long id, @PathVariable Long userId) {

//    }
}
