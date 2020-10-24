package com.task.todo.exceptions;

public class UserDoesNotExistException extends RuntimeException {

  private String message;

  public UserDoesNotExistException(Long id) {
    this.message = "User does not exist with id: " + id;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
