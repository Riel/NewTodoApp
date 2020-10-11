package com.task.todo.exceptions;

public class ExpiredVerificationTokenException extends RuntimeException {
  private String message;

  public ExpiredVerificationTokenException(String message) {
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
