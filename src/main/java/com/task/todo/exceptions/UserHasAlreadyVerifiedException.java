package com.task.todo.exceptions;

public class UserHasAlreadyVerifiedException extends RuntimeException {
  private String message;

  public UserHasAlreadyVerifiedException(String message) {
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
