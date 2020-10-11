package com.task.todo.security;


import com.task.todo.models.User;
import com.task.todo.repositories.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class TodoUserDetailsService implements UserDetailsService {
  private UserRepository userRepository;

  public TodoUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findFirstByUsername(username);
    user.orElseThrow(() -> new UsernameNotFoundException("User not found with the name: " + username));
    return new TodoUserDetails(user.get());
  }
}
