package com.task.todo.exceptions;


public class UserSettingDoesNotExistException extends RuntimeException {

  private String message;

  public UserSettingDoesNotExistException(Long id) {
    this.message = "User setting does not exist for user id: " + id;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
