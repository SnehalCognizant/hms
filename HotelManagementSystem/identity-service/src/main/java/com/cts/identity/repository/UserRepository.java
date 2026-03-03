package com.cts.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.identity.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
