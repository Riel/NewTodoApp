package com.task.todo.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne (fetch = FetchType.LAZY)
  private Setting setting;

  private String name;
  //endregion


  public Project() {
  }

  public Project(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSetting(Setting setting) {
    this.setting = setting;
  }

  @Override
  public String toString(){
    return name;
  }
}
