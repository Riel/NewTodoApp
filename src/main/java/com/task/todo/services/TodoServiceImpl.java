package com.task.todo.services;

import com.task.todo.dtos.DisplayTodoDTO;
import com.task.todo.models.Context;
import com.task.todo.models.Project;
import com.task.todo.utilities.DateUtilities;
import com.task.todo.utilities.TodoColorizer;
import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Todo;
import com.task.todo.models.User;
import com.task.todo.repositories.TodoRepository;
import com.task.todo.repositories.UserRepository;
import com.task.todo.utilities.TodoColors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Query;
import jdk.vm.ci.meta.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl {

  private UserRepository userRepository;
  private TodoRepository todoRepository;
  private QueryService queryService;


  @Autowired
  public TodoServiceImpl(UserRepository userRepository, TodoRepository todoRepository,
                         QueryService queryService) {
    this.userRepository = userRepository;
    this.todoRepository = todoRepository;
    this.queryService = queryService;
  }

  public List<DisplayTodoDTO> getFilteredTodos(String ownerName, String project, String context){
    Query query = queryService.buildFilterQuery(ownerName, project, context);
    List<Todo> todos = query.getResultList();

    return todos.stream()
        .map(todo -> convertTodoToDisplayTodoDTO(todo))
        .collect(Collectors.toList());

    /*List<TodoColorizer> todoViewModels = new ArrayList<>();
    todos.stream().forEach(todo -> todoViewModels.add(new TodoColorizer(todo)));
    return todoViewModels;*/
  }


  private DisplayTodoDTO convertTodoToDisplayTodoDTO(Todo todo){
    ModelMapper mapper = new ModelMapper();
    DisplayTodoDTO dto = new DisplayTodoDTO();
    LocalDate dueDate = todo.getDueDate();
    TodoColorizer colorizer = new TodoColorizer(todo);
    mapper.createTypeMap(Todo.class, DisplayTodoDTO.class)
        .setPostConverter(context -> {
      context.getDestination().setPriorityDisplayColor(colorizer.getPriorityDisplayColor());
      context.getDestination().setDueDateDisplayColor(colorizer.getDueDateDisplayColor());
      context.getDestination().setProjectDisplayColor(colorizer.getProjectDisplayColor());
      context.getDestination().setContextDisplayColor(colorizer.getContextDisplayColor());
      //context.getDestination().setCreationDisplayDate(DateUtilities.localDateToString(todo.getCreationDate()));
      context.getDestination().setDueDisplayDate(dueDate == null ? "-" : DateUtilities.localDateToString(dueDate));
      return context.getDestination();
    }).map(todo, dto);

    // Set colors:


    return dto;
  }

  public Todo getTodo(Long id) {
    Optional<Todo> todo = todoRepository.findById(id);
    return todo.isPresent() ? todo.get() : null ;
  }

  public Todo getInstantOrNormalTodo(Long id){
    Todo todo = getTodo(id);
    if(todo.getProject()==null || "not set".equals(todo.getProject())){
      String title = todo.getTitle();
      todo = createEmptyTodo();
      todo.setTitle(title);
      todo.setId(id);
    }

    return todo;
  }

  public Todo createEmptyTodo() {
    Todo todo = new Todo("", "", new Project("Home"), new Context("Phone"), Priority.LOW, Status.NOT_STARTED, null, new User());
    todo.setId(0L);
    return todo;
  }

  public void saveInstantTodo(String title){
    /*
    List<User> owners = new ArrayList();
    getUsers().forEach(o -> owners.add(o));
    Todo instantTodo = new Todo(title, "", "not set", "not set", Priority.MUST, Status.NOT_STARTED, LocalDate
        .now(), owners.get(0));

    saveTodo(instantTodo);*/
  }

  public void saveTodo(Todo todo){
    todoRepository.save(todo);
  }

  public void deleteTodoById(Long id) {
    todoRepository.deleteById(id);
  }

  public void completeTodo(Long id){
    Todo todo = getTodo(id);
    todo.setStatus(Status.FINISHED);
    saveTodo(todo);
  }
}