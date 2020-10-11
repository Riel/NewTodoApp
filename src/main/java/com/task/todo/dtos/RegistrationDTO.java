package com.task.todo.dtos;

public class RegistrationDTO {

  //region Fields
  private String username;
  private String email;
  private String password;
  //endregion

  //region Constructors
  public RegistrationDTO() {
  }

  public RegistrationDTO(String username, String email, String password) {
    this.username = username;
    this.password = password;
    this.email = email;
  }
  //endregion

  //region Getters & Setters
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  //endregion
}
