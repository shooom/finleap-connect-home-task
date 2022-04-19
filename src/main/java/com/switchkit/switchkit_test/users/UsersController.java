package com.switchkit.switchkit_test.users;

import com.switchkit.switchkit_test.users.dto.UserCreateDto;
import com.switchkit.switchkit_test.users.proections.UserProjection;
import com.switchkit.switchkit_test.users.proections.UserRolesProjection;
import com.switchkit.switchkit_test.utils.ProjectionsConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;
    private final ProjectionsConverter converter;

    @PostMapping
    public ResponseEntity<UserProjection> addUser(@RequestBody UserCreateDto dto) {
        var newUser = service.createUser(
                dto.getUsername(), dto.getPassword(), dto.getRoles());
        return ok(converter.getProjection(UserRolesProjection.class, newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRolesProjection> updateUser(@PathVariable("id") Long id, @RequestBody UserCreateDto dto) {
        var user = service.updateUser(id, dto);
        return ok(converter.getProjection(UserRolesProjection.class, user));
    }

    @GetMapping
    public ResponseEntity<List<UserRolesProjection>> getUsers() {
        return ok(service.getAllUsers()
                .stream()
                .map(user -> converter.getProjection(UserRolesProjection.class, user))
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
    }
}
