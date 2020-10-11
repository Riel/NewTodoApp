package com.task.todo.repositories;


import com.task.todo.email.VerificationToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  @EntityGraph(attributePaths = {"user"})
  Optional<VerificationToken> findByToken(String token);
}
