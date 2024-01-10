package com.securityJava.springsecurity.repository;


import com.securityJava.springsecurity.entities.Role;
import com.securityJava.springsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer > {
    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
