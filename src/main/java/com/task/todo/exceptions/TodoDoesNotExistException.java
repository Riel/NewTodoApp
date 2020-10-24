package com.task.todo.exceptions;

public class TodoDoesNotExistException extends RuntimeException {

  private String message;

  public TodoDoesNotExistException(Long id) {
    this.message = "Todo does not exist with id: " + id;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
