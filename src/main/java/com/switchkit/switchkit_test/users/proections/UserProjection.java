package com.switchkit.switchkit_test.users.proections;

import com.switchkit.switchkit_test.users.models.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "planeUser", types = {User.class})
public interface UserProjection {
    long getId();
    String getUsername();
}
