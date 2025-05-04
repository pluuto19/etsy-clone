package project.semester.aspm.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.semester.aspm.userservice.model.VerificationToken;
import project.semester.aspm.userservice.model.User;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUser(User user);
} 