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
  private Project lookupProject;

  @OneToOne(fetch = FetchType.LAZY)
  private Context lookupContext;

  @OneToOne(fetch = FetchType.LAZY)
  private User lookupAssignee;

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

  public Project getLookupProject() {
    return lookupProject;
  }

  public void setLookupProject(Project lookupProject) {
    this.lookupProject = lookupProject;
  }

  public Context getLookupContext() {
    return lookupContext;
  }

  public void setLookupContext(Context lookupContext) {
    this.lookupContext = lookupContext;
  }

  public User getLookupAssignee() {
    return lookupAssignee;
  }

  public void setLookupAssignee(User lookupAssignee) {
    this.lookupAssignee = lookupAssignee;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
  //endregion

  public String getLookupAssingeeName(){
    return lookupAssignee == null ? null : lookupAssignee.getUsername();
  }

  public String getLookupProjectName(){
    return lookupProject == null ? null : lookupProject.getName();
  }

  public String getLookupContextName(){
    return lookupContext == null ? null : lookupContext.getName();
  }
}
