package com.switchkit.switchkit_test.users;

import com.switchkit.switchkit_test.users.models.User;
import com.switchkit.switchkit_test.users.repository.RoleRepository;
import com.switchkit.switchkit_test.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passCoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User createUser(String username, String password, String[] roles) {
        var user = new User(
                username,
                passCoder.encode(password),
                roleRepository.findAllByAuthorityIn(roles));
        return repository.save(user);
    }

    public User updateUser(Long id, UserCreateDto dto) {
        return repository.findById(id)
                        .map(user ->{
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
