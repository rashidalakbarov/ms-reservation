package com.barber.reservation.repository;

import com.barber.reservation.domain.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(@NotBlank String username);

    boolean existsByEmail(String email);
}
