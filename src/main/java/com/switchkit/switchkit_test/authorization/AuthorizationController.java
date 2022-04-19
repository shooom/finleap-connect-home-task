package com.switchkit.switchkit_test.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDto requestDto) {
        return ResponseEntity.ok(service.getNewJwt(requestDto));
    }
}
