package com.switchkit.switchkit_test.users.proections;

import com.switchkit.switchkit_test.users.models.Role;
import com.switchkit.switchkit_test.users.models.User;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(
        name = "UserWithRoles",
        types = {User.class, Role.class}
)
public interface UserRolesProjection extends UserProjection {
    List<RoleProjection> getAuthorities();
}
