package com.task.todo.dtos;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.User;
import java.time.LocalDate;

public class SimpleTodoDTO {

  //region Fields
  private Long id;
  private String title;
  private String description;
  private String link;
  private String project;
  private String context;
  private Priority priority;
  private Status status;
  private boolean isDeleted;
  private boolean isPublic;
  private String history;
  private LocalDate dueDate;
  private LocalDate completionDate;
  private User creator;
  private User assignee;

  private String priorityDisplayClass;
  private String dueDateDisplayColor;
  private String projectDisplayColor;
  private String contextDisplayColor;

  private String dueDisplayDate;
  //endregion

  //region Direct Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }

  public boolean isPublic() {
    return isPublic;
  }

  public void setPublic(boolean aPublic) {
    isPublic = aPublic;
  }

  public String getHistory() {
    return history;
  }

  public void setHistory(String history) {
    this.history = history;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDate getCompletionDate() {
    return completionDate;
  }

  public void setCompletionDate(LocalDate completionDate) {
    this.completionDate = completionDate;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public User getAssignee() {
    return assignee;
  }

  public void setAssignee(User assignee) {
    this.assignee = assignee;
  }
  //endregion

  //region Indirect Setters
  public void setPriorityDisplayClass(String priorityDisplayClass) {
    this.priorityDisplayClass = priorityDisplayClass;
  }

  public void setDueDateDisplayColor(String dueDateDisplayColor) {
    this.dueDateDisplayColor = dueDateDisplayColor;
  }

  public void setProjectDisplayColor(String projectDisplayColor) {
    this.projectDisplayColor = projectDisplayColor;
  }

  public void setContextDisplayColor(String contextDisplayColor) {
    this.contextDisplayColor = contextDisplayColor;
  }

  public void setDueDisplayDate(String dueDisplayDate) {
    this.dueDisplayDate = dueDisplayDate;
  }
  //endregion

  //region Indirect Getters
  public boolean hasLink(){
    return link != null;
  }

  public String getPriorityDisplayClass(){
    return priorityDisplayClass;
  }

  public String getDueDateDisplayColor(){
    return dueDateDisplayColor;
  }

  public String getProjectDisplayColor(){
    return projectDisplayColor;
  }

  public String getContextDisplayColor(){
    return contextDisplayColor;
  }

  public String getDueDisplayDate() {
    return dueDisplayDate;
  }
  //endregion
}
