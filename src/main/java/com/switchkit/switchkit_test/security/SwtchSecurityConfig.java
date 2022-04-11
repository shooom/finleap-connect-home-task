package com.switchkit.switchkit_test.security;

import com.switchkit.switchkit_test.users.RoleService;
import com.switchkit.switchkit_test.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SwtchSecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        roleService.createRole("ADMIN");
        roleService.createRole("USER");
        String[] roles = {"ADMIN"};
        userService.createUser("admin", "admin", roles);

        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
        http.headers().frameOptions().disable();
        http.csrf().disable();
    }
}
