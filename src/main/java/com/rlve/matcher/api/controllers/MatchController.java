package com.rlve.matcher.api.controllers;

import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.User;
import com.rlve.matcher.api.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        matchService.signUserToMatch(111L,128L);
        matchService.signUserToMatch(116L,128L);
        matchService.signUserToMatch(121L,128L);
    }

    @GetMapping("/custom2")
    public void Custom2(){
        matchService.removeUserFromMatch(111L,128L);
//        matchService.removeUserFromMatch(116L,128L);
//        matchService.removeUserFromMatch(121L,128L);
    }

//    @PostMapping("/matches/{id}/sign/{userId}")
//    public ResponseEntity<Object> signUser(@PathVariable Long id, @PathVariable Long userId) {

//    }
}
