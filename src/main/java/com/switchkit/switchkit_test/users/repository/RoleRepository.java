package com.switchkit.switchkit_test.users.repository;

import com.switchkit.switchkit_test.users.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findAllByAuthorityIn(String[] authorities);
}
