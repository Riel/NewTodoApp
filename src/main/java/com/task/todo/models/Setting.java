package com.task.todo.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "settings")
public class Setting {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany (cascade = CascadeType.ALL, mappedBy = "setting")
  private List<Context> contexts = new ArrayList<>();

  @OneToMany (cascade = CascadeType.ALL, mappedBy = "setting")
  private List<Project> projects = new ArrayList<>();
  // private boolean showDone;
  //endregion

  //region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /*public boolean isShowDone() {
    return showDone;
  }

  public void setShowDone(boolean showDone) {
    this.showDone = showDone;
  }*/

  public List<Context> getContexts() {
    return contexts;
  }

  public List<Project> getProjects() {
    return projects;
  }
  //endregion

  // TODO: might need to be replaced with a setter if not used
  public void addContext(Context context) {
    contexts.add(context);
  }

  public void addProject(Project project) {
    projects.add(project);
  }
}