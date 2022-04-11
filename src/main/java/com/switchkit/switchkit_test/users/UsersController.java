package com.switchkit.switchkit_test.users;

import com.switchkit.switchkit_test.users.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<User> addUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("roles") String[] roles) {
        var newUser = service.createUser(username, password, roles);
        return ResponseEntity.ok(newUser);
    }
}
