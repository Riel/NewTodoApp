package com.task.todo.exceptions;

public class UnexpectedEnumValueException extends RuntimeException {

  private String message;

  public UnexpectedEnumValueException(String enumName) {
    this.message = "Unexpected value for enum: " + enumName;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
