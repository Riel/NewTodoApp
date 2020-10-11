package com.task.todo.exceptions;

public class VerificationTokenDoesNotExistException extends RuntimeException {

  private String message;

  public VerificationTokenDoesNotExistException(String message) {
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
