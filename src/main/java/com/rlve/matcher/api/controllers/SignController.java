package com.rlve.matcher.api.controllers;

import com.rlve.matcher.api.services.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SignController {

    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }


    @GetMapping("/custom")
    public void Custom(){
        signService.signUserToMatch(111L,128L);
        signService.signUserToMatch(116L,128L);
        signService.signUserToMatch(121L,128L);
    }

    @GetMapping("/custom2")
    public void Custom2(){
        signService.removeUserFromMatch(111L,128L);
    }

    @GetMapping("/custom3")
    public void Custom3(){
        signService.removeUserFromMatch(116L,128L);
    }

    @GetMapping("/custom4")
    public void Custom4(){
        signService.removeUserFromMatch(121L,128L);
    }

//    @PostMapping("/matches/{id}/sign/{userId}")
//    public ResponseEntity<Object> signUser(@PathVariable Long id, @PathVariable Long userId) {

//    }
}
