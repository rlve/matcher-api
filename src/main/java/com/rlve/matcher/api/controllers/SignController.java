package com.rlve.matcher.api.controllers;

import com.rlve.matcher.api.domain.MatchState;
import com.rlve.matcher.api.exceptions.SquadException;
import com.rlve.matcher.api.services.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SignController {

    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    @GetMapping("/sign/user/{userId}/match/{matchId}")
    public ResponseEntity<Object> signUserToMatch(@PathVariable Long userId, @PathVariable Long matchId ){
        MatchState result = signService.signUserToMatch(userId, matchId);
        String message = result.getMessage();

        if (result.getSignState() == MatchState.SIGN.IN_RESERVES || result.getSignState() == MatchState.SIGN.IN_SQUAD ) {
            throw new SquadException(String.format("%s id: %s", message, userId));
        }

        return ResponseEntity.ok().body(message);

    }

    @GetMapping("/remove/user/{userId}/match/{matchId}")
    public ResponseEntity<Object> removeUserFromMatch(@PathVariable Long userId, @PathVariable Long matchId ){
        MatchState result = signService.removeUserFromMatch(userId, matchId);

        String message = result.getMessage();

        return ResponseEntity.ok().body(message);
    }
}
