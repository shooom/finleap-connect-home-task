package com.switchkit.switchkit_test.users;

import lombok.Data;

@Data
public class UserCreateDto {
    private String username;
    private String password;
    private String[] roles;
}
