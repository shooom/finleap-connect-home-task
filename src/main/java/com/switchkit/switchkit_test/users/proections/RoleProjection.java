package com.switchkit.switchkit_test.users.proections;

import com.switchkit.switchkit_test.users.models.Role;
import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "planeRole",
        types = {Role.class}
)
public interface RoleProjection {
    String getAuthority();
}
