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

  @OneToOne(fetch = FetchType.LAZY)
  private Project lookUpProject;

  @OneToOne(fetch = FetchType.LAZY)
  private Context lookUpContext;

  @OneToOne(fetch = FetchType.LAZY)
  private User lookUpAssignee;

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

  public Project getLookUpProject() {
    return lookUpProject;
  }

  public void setLookUpProject(Project lookUpProject) {
    this.lookUpProject = lookUpProject;
  }

  public Context getLookUpContext() {
    return lookUpContext;
  }

  public void setLookUpContext(Context lookUpContext) {
    this.lookUpContext = lookUpContext;
  }

  public User getLookUpAssignee() {
    return lookUpAssignee;
  }

  public void setLookUpAssignee(User lookUpAssignee) {
    this.lookUpAssignee = lookUpAssignee;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
  //endregion

  public String getLookupAssingeeName(){
    return lookUpAssignee == null ? null : lookUpAssignee.getUsername();
  }

  public String getLookupProjectName(){
    return lookUpProject == null ? null : lookUpProject.getName();
  }

  public String getLookupContextName(){
    return lookUpContext == null ? null : lookUpContext.getName();
  }
}
