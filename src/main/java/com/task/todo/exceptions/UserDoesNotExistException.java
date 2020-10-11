package com.task.todo.exceptions;

public class UserDoesNotExistException extends RuntimeException {

  private String message;

  public UserDoesNotExistException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
