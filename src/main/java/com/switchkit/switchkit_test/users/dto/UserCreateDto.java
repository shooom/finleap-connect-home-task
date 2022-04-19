package com.switchkit.switchkit_test.users.dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String username;
    private String password;
    private String[] roles;
}
