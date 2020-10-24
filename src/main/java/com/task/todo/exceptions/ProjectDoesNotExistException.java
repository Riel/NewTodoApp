package com.task.todo.exceptions;

public class ProjectDoesNotExistException extends RuntimeException {

  private String message;

  public ProjectDoesNotExistException(String name) {
    this.message = "Project does not exist with name: " + name;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
