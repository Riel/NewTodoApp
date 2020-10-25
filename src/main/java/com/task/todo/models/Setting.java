package com.task.todo.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
  private List<Context> contexts;

  @OneToMany (cascade = CascadeType.ALL, mappedBy = "setting")
  private List<Project> projects;
  //endregion


  public Setting() {
    contexts = new ArrayList<>();
    projects = new ArrayList<>();
  }

  //region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Context> getContexts() {
    return contexts;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setContexts(List<Context> contexts) {
    this.contexts = contexts;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }
  //endregion


  public void addContext(Context context) {
    contexts.add(context);
    context.setSetting(this);
  }

  public void addProject(Project project) {
    projects.add(project);
    project.setSetting(this);
  }
}