package com.task.todo.services;

import com.task.todo.dtos.FullTodoDTO;
import com.task.todo.dtos.SimpleTodoDTO;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Query;
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

  //region Collect todos
  public List<SimpleTodoDTO> getFilteredTodos(String ownerName, String project, String context){
    Query query = queryService.buildFilterQuery(ownerName, project, context);
    List<Todo> todos = query.getResultList();

    return todos.stream()
        .map(todo -> convertTodoToSimpleTodoDTO(todo))
        .collect(Collectors.toList());
  }

  public FullTodoDTO getTodoDto(Long id) {
    Optional<Todo> todo = todoRepository.findById(id);
    return todo.isPresent() ? convertTodoToFullTodoDto(todo.get()) : null;
  }

  public FullTodoDTO getInstantOrNormalTodo(Long id){
    FullTodoDTO dto = getTodoDto(id);
    if(dto.getProject()==null || "not set".equals(dto.getProject())){
      String title = dto.getTitle();
      dto =  createEmptyTodo();
      dto.setTitle(title);
      dto.setId(id);
    }

    return dto;
  }

  public FullTodoDTO createEmptyTodo() {
    Todo todo = new Todo("", "", new Project("Home"), new Context("Phone"), Priority.LOW, Status.NOT_STARTED, null, new User());
    todo.setId(0L);
    return convertTodoToFullTodoDto(todo);
  }
  //endregion

  //region Modify todos
  public void saveInstantTodo(String title){
    /*
    List<User> owners = new ArrayList();
    getUsers().forEach(o -> owners.add(o));
    Todo instantTodo = new Todo(title, "", "not set", "not set", Priority.MUST, Status.NOT_STARTED, LocalDate
        .now(), owners.get(0));

    saveTodo(instantTodo);*/
  }

  public void saveTodo(FullTodoDTO dto){
    todoRepository.save(convertFullTodoDtoToTodo(dto));
  }

  public void deleteTodoById(Long id) {
    todoRepository.deleteById(id);
  }

  public void completeTodo(Long id){
    Todo todo = getTodoById(id);
    todo.setStatus(Status.FINISHED);
    todoRepository.save(todo);
  }
  //endregion

  private Todo getTodoById(Long id){
    Optional<Todo> todo = todoRepository.findById(id);
    return todo.isPresent() ? todo.get() : null;
  }

  //region Mapping to / from Dto
  private SimpleTodoDTO convertTodoToSimpleTodoDTO(Todo todo){
    ModelMapper mapper = new ModelMapper();
    SimpleTodoDTO dto = new SimpleTodoDTO();
    LocalDate dueDate = todo.getDueDate();
    TodoColorizer colorizer = new TodoColorizer(todo);
    mapper.createTypeMap(Todo.class, SimpleTodoDTO.class)
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

  private FullTodoDTO convertTodoToFullTodoDto(Todo todo){
    ModelMapper mapper = new ModelMapper();
    FullTodoDTO dto = new FullTodoDTO();
    mapper.createTypeMap(Todo.class, FullTodoDTO.class).map(todo, dto);
    return dto;
  }

  private Todo convertFullTodoDtoToTodo(FullTodoDTO dto){
    ModelMapper mapper = new ModelMapper();
    Todo todo = new Todo();
    mapper.createTypeMap(FullTodoDTO.class, Todo.class).map(dto, todo);
    return todo;
  }
  //endregion
}