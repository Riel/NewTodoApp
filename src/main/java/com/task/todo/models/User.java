package com.task.todo.models;

import com.task.todo.email.VerificationToken;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private boolean verified;
  private boolean active;

  @Column(nullable = false)
  private String roles = "";

  @Column(nullable = false)
  private LocalDateTime registrationDate;

  @OneToMany(mappedBy = "assignee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<Todo> todos;

  @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST},
      fetch = FetchType.LAZY, mappedBy = "user")
  private VerificationToken verificationToken;
  //endregion

  public User(){
    todos = new ArrayList<>();
    registrationDate = LocalDateTime.now();
  }

  public User(String username, String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User(String username, String email, String password, String roles) {
    this(username, email, password);
    this.roles = roles;
  }

  //region Getters & Setters
  public long getId() {
    return id;
  }

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

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }

  public List<Todo> getTodos() {
    return todos;
  }


  public VerificationToken getVerificationToken() {
    return verificationToken;
  }

  public void setVerificationToken(VerificationToken verificationToken) {
    this.verificationToken = verificationToken;
  }
  //endregion

  //region Methods
  public void addTodo(Todo newTodo) {
    newTodo.setAssignee(this);
    todos.add(newTodo);
  }

  public List<String> getRoleList() {
    if (this.roles.length() > 0) {
      return Arrays.asList(this.roles.split(";"));
    }
    return new ArrayList<>();
  }
  //endregion
}