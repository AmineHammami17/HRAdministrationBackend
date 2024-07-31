package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);

    Optional<User> findByIdAndRole(Long id, String role);

    Optional<User> findByResetToken(String resetToken);
}
