package com.task.todo.repositories;

import com.task.todo.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  void deleteUserByUsername(String username);
  Optional<User> findFirstByUsername(String username);

  Optional<User> findFirstByEmail(String username);

  @Query(value = "SELECT u.username FROM User u")
  List<String> findAllUsername();

  User save(User user);
}