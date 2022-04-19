package com.switchkit.switchkit_test.users;

import com.switchkit.switchkit_test.users.models.User;
import com.switchkit.switchkit_test.users.repository.RoleRepository;
import com.switchkit.switchkit_test.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User findUser(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("User not found");
        });
    }

    public User findUserByName(String username) {
        return repository.findUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User createUser(String username, String password, String[] roles) {
        var user = new User(
                username,
                passwordEncoder.encode(password),
                roleRepository.findAllByAuthorityIn(roles));
        return repository.save(user);
    }

    public User updateUser(Long id, UserCreateDto dto) {
        return repository.findById(id)
                        .map(user -> {
                            user.setUsername(dto.getUsername());
                            user.setAuthorities(roleRepository.findAllByAuthorityIn(dto.getRoles()));
                            return repository.save(user);
                        })
                        .orElseGet(() -> {
                            return repository.save(new User(
                                    dto.getUsername(),
                                    roleRepository.findAllByAuthorityIn(dto.getRoles()))
                            );
                        });
    }

    public void deleteUser(Long id) {
        repository.findById(id)
                .map(user -> {
                    user.setAccountNonLocked(false);
                    return repository.save(user);
                })
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found");
                });
    }
}
