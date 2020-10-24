package com.task.todo.exceptions;

public class ContextDoesNotExistException extends RuntimeException {

  private String message;

  public ContextDoesNotExistException(String name) {
    this.message = "Context does not exist with name: " + name;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
