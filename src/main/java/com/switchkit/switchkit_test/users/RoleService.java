package com.switchkit.switchkit_test.users;

import com.switchkit.switchkit_test.users.models.Role;
import com.switchkit.switchkit_test.users.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Role createRole(String role) {
        return repository.save(new Role(role));
    }

    public Set<Role> getRoles(String[] roles) {
        return repository.findAllByAuthorityIn(roles);
    }
}
