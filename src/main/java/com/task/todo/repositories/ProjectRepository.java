package com.task.todo.repositories;

import com.task.todo.models.Project;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

  Optional<Project> findByName(String name);
}
