package com.task.todo.services;

import com.task.todo.exceptions.PrincipalIsNotTodoUserDetailsException;
import com.task.todo.exceptions.UserDoesNotExistException;
import com.task.todo.models.User;
import com.task.todo.repositories.UserRepository;
import com.task.todo.security.TodoUserDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

  private UserRepository userRepository;
  private QueryService queryService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
                         QueryService queryService) {
    this.userRepository = userRepository;
    this.queryService = queryService;
  }

  public Iterable<User> getUsers() {
    return userRepository.findAll();
  }

  public List<String> getUserNames() {
    return userRepository.findAllUsername();
  }

  public User getAuthenticatedUserWithoutProperties() {
    long authenticatedUserId = getAuthenticatedUserId();
    Optional<User> optionalUser = userRepository.findById(authenticatedUserId);
    return optionalUser.orElseThrow(() -> new UserDoesNotExistException(Long.toString(authenticatedUserId)));
  }

  private long getAuthenticatedUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof TodoUserDetails)) {
      throw new PrincipalIsNotTodoUserDetailsException();
    }
    return ((TodoUserDetails) principal).getUserId();
  }


  public void deleteUser(String name){
    userRepository.deleteUserByUsername(name);
  }


  public User saveUser(User newAssignee){
    return userRepository.save(newAssignee);
  }
}
