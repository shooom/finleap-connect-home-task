package com.switchkit.switchkit_test.security;

import com.switchkit.switchkit_test.jwt.JwtConfigurer;
import com.switchkit.switchkit_test.jwt.JwtTokenProvider;
import com.switchkit.switchkit_test.users.RoleService;
import com.switchkit.switchkit_test.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SwtchSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String LOGIN_ENDPOINT = "/auth/login";
    private final static String USERS_ENDPOINT = "/users/**";

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final RoleService roleService;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(USERS_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(tokenProvider));
    }

    @PostConstruct
    private void initAdmin() {
        log.info("Create [ADMIN] user...");
        roleService.createRole("ADMIN");
        roleService.createRole("USER");
        String[] roles = {"ADMIN"};
        userService.createUser("admin", "admin", roles);
        log.info("Done.");
    }
}
