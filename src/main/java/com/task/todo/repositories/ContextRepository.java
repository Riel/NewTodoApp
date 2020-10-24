package com.task.todo.repositories;

import com.task.todo.models.Context;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ContextRepository extends CrudRepository<Context, Integer> {

  Optional<Context> findByName(String name);
}
