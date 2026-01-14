package com.cineworld.cinemetro.persistence.repository.user;

import com.cineworld.cinemetro.domain.enums.user.UserRole;
import com.cineworld.cinemetro.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    boolean existsByEmail(String email);
}