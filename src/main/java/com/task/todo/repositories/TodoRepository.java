package com.task.todo.repositories;

import com.task.todo.models.Context;
import com.task.todo.models.Project;
import com.task.todo.models.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
  List<Todo> findAll();
  List<Todo> findAllByProject(Project project);
  List<Todo> findAllByContext(Context context);
}