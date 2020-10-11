package com.task.todo.exceptions;

public class GlobalSettingDoesNotExistException extends RuntimeException {

  private String message;

  public GlobalSettingDoesNotExistException(String message) {
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
