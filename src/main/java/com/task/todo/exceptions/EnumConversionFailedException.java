package com.task.todo.exceptions;

public class EnumConversionFailedException extends RuntimeException {

  private String message;

  public EnumConversionFailedException(String value, String enumName) {
    this.message = "Conversion of value: " + value + " to " + enumName + " failed";
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
