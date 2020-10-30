package com.task.todo.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_settings")
public class UserSetting {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private boolean displayDone;

  private String lookUpProjectName;

  private String lookUpContextName;

  private String lookUpAssigneeName;

  @OneToOne(fetch = FetchType.LAZY)
  private User user;
  //endregion


  //region Getters & Setters
  public boolean isDisplayDone() {
    return displayDone;
  }

  public void setDisplayDone(boolean displayDone) {
    this.displayDone = displayDone;
  }

  public String getLookUpProjectName() {
    return lookUpProjectName;
  }

  public void setLookUpProjectName(String lookUpProject) {
    this.lookUpProjectName = lookUpProject;
  }

  public String getLookUpContextName() {
    return lookUpContextName;
  }

  public void setLookUpContextname(String lookUpContext) {
    this.lookUpContextName = lookUpContext;
  }

  public String getLookUpAssigneeName() {
    return lookUpAssigneeName;
  }

  public void setLookUpAssigneeName(String lookUpAssigneeName) {
    this.lookUpAssigneeName = lookUpAssigneeName;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
  //endregion
}
