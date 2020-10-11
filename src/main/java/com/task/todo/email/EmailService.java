package com.task.todo.email;


import com.task.todo.models.User;

public interface EmailService {
  void sendVerificationEmail(User user);
}
