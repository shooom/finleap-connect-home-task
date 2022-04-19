package com.switchkit.switchkit_test.authorization;

import com.switchkit.switchkit_test.jwt.JwtTokenProvider;
import com.switchkit.switchkit_test.users.UserService;
import com.switchkit.switchkit_test.users.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public Map<Object, Object> getNewJwt(AuthenticationDto dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            User user = userService.findUserByName(dto.getUsername());

            if(user == null) {
                throw new UsernameNotFoundException("User with name " + dto.getUsername() + " not found");
            }

            String token = tokenProvider.createToken(dto.getUsername(), user.getAuthorities());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", dto.getUsername());
            response.put("token", token);

            return response;
        } catch (AuthenticationException ex) {
            log.error("Invalid username or password");
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
