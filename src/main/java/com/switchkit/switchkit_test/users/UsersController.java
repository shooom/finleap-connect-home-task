package com.switchkit.switchkit_test.users;

import com.switchkit.switchkit_test.users.models.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserCreateDto dto) {
        var newUser = service.createUser(
                dto.getUsername(), dto.getPassword(), dto.getRoles());
        return ok(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserCreateDto dto) {
        return ok(service.updateUser(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ok(service.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
    }
}
