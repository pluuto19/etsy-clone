package project.semester.aspm.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.semester.aspm.userservice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
} 