package com.task.todo.services;

import static java.time.temporal.ChronoUnit.DAYS;

import com.task.todo.dtos.FullTodoDTO;
import com.task.todo.dtos.SimpleTodoDTO;
import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.exceptions.ContextDoesNotExistException;
import com.task.todo.exceptions.ProjectDoesNotExistException;
import com.task.todo.exceptions.TodoDoesNotExistException;
import com.task.todo.models.Context;
import com.task.todo.models.Project;
import com.task.todo.models.Todo;
import com.task.todo.models.User;
import com.task.todo.repositories.ContextRepository;
import com.task.todo.repositories.ProjectRepository;
import com.task.todo.repositories.TodoRepository;
import com.task.todo.utilities.DateUtilities;
import com.task.todo.utilities.TodoColorizer;
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

  private UserServiceImpl userService;
  private TodoRepository todoRepository;
  private ProjectRepository projectRepository;
  private ContextRepository contextRepository;
  private QueryService queryService;

  @Autowired
  public TodoServiceImpl(UserServiceImpl userService,
                         TodoRepository todoRepository,
                         ProjectRepository projectRepository,
                         ContextRepository contextRepository,
                         QueryService queryService) {
    this.userService = userService;
    this.todoRepository = todoRepository;
    this.projectRepository = projectRepository;
    this.contextRepository = contextRepository;
    this.queryService = queryService;
  }

  //region Collect todos
  public List<SimpleTodoDTO> getFilteredTodos(String ownerName, String projectName, String contextName){
    Query query = queryService.buildFilterQuery(ownerName, projectName, contextName);
    List<Todo> requestedTodos = query.getResultList();

    List<SimpleTodoDTO> requestedDTOs = requestedTodos.stream()
        .map(todo -> convertTodoToSimpleTodoDTO(todo))
        .collect(Collectors.toList());

    insertEmptyDTOs(requestedDTOs);
    return requestedDTOs;
  }

  private void insertEmptyDTOs(List<SimpleTodoDTO> requestedDTOs) {
    if (requestedDTOs.size() == 0){
      return;
    }

    Optional<SimpleTodoDTO> firstActiveDTO = requestedDTOs.stream()
        .filter(dto -> dto.hasDueDate() && getDaysAvailable(dto) >= 0 )
        .findFirst();

    Optional<SimpleTodoDTO> firstNoDueDateDTO = requestedDTOs.stream()
        .filter(dto -> !dto.hasDueDate())
        .findFirst();

    insertPlaceholderBefore(requestedDTOs, firstActiveDTO);
    insertPlaceholderBefore(requestedDTOs, firstNoDueDateDTO);
  }

  private void insertPlaceholderBefore(List<SimpleTodoDTO> requestedDTOs,
                                       Optional<SimpleTodoDTO> firstDTO) {
    int index = requestedDTOs.indexOf(firstDTO.get());
    if (firstDTO.isPresent() && index > 0) {
      SimpleTodoDTO placeHolder = new SimpleTodoDTO();
      placeHolder.setIsSpaceHolder(true);
      requestedDTOs.add(index, placeHolder);
    }
  }

  private long getDaysAvailable(SimpleTodoDTO dto){
    return DAYS.between(LocalDate.now(), dto.getDueDate());
  }

  public FullTodoDTO getTodoDto(Long id) {
    Optional<Todo> todo = todoRepository.findById(id);
    if (todo.isPresent()){
      return convertTodoToFullTodoDto(todo.get());
    } else {
      throw new TodoDoesNotExistException(id);
    }
  }

  public FullTodoDTO getInstantOrNormalTodo(Long id){
    FullTodoDTO dto = getTodoDto(id);
    if(dto.getIsInstant()){
      String title = dto.getTitle();
      dto =  createInstantTodoDto();
      dto.setTitle(title);
      dto.setId(id);
    }

    return dto;
  }

  public FullTodoDTO createInstantTodoDto() {
    Todo todo = createInstantTodo();
    todo.setId(0L);
    return convertTodoToFullTodoDto(todo);
  }
  //endregion

  //region Modify todos
  public void saveInstantTodo(String title){
    Todo instantTodo = createInstantTodo();
    instantTodo.setTitle(title);
    todoRepository.save(instantTodo);
  }

  private Todo createInstantTodo() {
    User loggedInUser = userService.getAuthenticatedUserWithoutProperties();
    return new Todo("", "", null, null, Priority.MUST, Status.NOT_STARTED, null,
        loggedInUser, loggedInUser);
  }

  public void saveNewTodo(FullTodoDTO dto) {
    dto.setCreationDate(LocalDate.now());
    dto.setCreator(userService.getAuthenticatedUserWithoutProperties());
    saveTodo(dto);
  }

  public void saveTodo(FullTodoDTO dto){
    todoRepository.save(convertFullTodoDtoToTodo(dto));
  }

  public void deleteTodoById(Long id) {
    Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoDoesNotExistException(id));
    todo.setDeleted(true);
    todoRepository.save(todo);
  }

  public void completeTodo(Long id){
    Todo todo = getTodoById(id);
    todo.setStatus(Status.COMPLETED);
    todoRepository.save(todo);
  }
  //endregion

  private Todo getTodoById(Long id){
    Optional<Todo> todo = todoRepository.findById(id);
    return todo.orElseThrow(() -> new TodoDoesNotExistException(id));
  }

  //region Mapping to / from Dto
  private SimpleTodoDTO convertTodoToSimpleTodoDTO(Todo todo){
    ModelMapper mapper = new ModelMapper();
    SimpleTodoDTO dto = new SimpleTodoDTO();
    LocalDate dueDate = todo.getDueDate();
    TodoColorizer colorizer = new TodoColorizer(todo);
    mapper.createTypeMap(Todo.class, SimpleTodoDTO.class)
        .setPostConverter(context -> {
          context.getDestination().setPriorityDisplayClass(colorizer.getPriorityDisplayClass());
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
    Project project = todo.getProject();
    Context todoContext = todo.getContext();
    mapper.createTypeMap(Todo.class, FullTodoDTO.class)
        .setPostConverter(context -> {
          context.getDestination().setProject(project == null ? "not set" : project.getName());
          context.getDestination().setContext(todoContext == null ? "not set" : todoContext.getName());
          return context.getDestination();
        }).map(todo, dto);
    return dto;
  }

  private Todo convertFullTodoDtoToTodo(FullTodoDTO dto){
    ModelMapper mapper = new ModelMapper();
    Todo todo = new Todo();
    String projectName = dto.getProject();
    String contextName = dto.getContext();
    Project project = projectRepository.findByName(projectName).orElseThrow(() -> new ProjectDoesNotExistException(projectName));
    Context todoContext = contextRepository.findByName(contextName).orElseThrow(() -> new ContextDoesNotExistException(contextName));
    String link = dto.getLink() == "" ?  null : dto.getLink();


    mapper.createTypeMap(FullTodoDTO.class, Todo.class)
        .setPostConverter(context -> {
      context.getDestination().setProject(project);
      context.getDestination().setContext(todoContext);
      context.getDestination().setLink(link);

      if (dto.getId() != null){
        // TODO: handle error here
        Todo origin = todoRepository.findById(dto.getId()).get();
        context.getDestination().setCreator(origin.getCreator());
        context.getDestination().setCreationDate(origin.getCreationDate());
      }

      return context.getDestination();
    }).map(dto, todo);

    return todo;
  }
  //endregion
}