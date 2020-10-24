package com.task.todo.dtos;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.User;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class FullTodoDTO {

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

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate creationDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dueDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate completionDate;

  private User creator;
  private User assignee;
  //endregion

  //region Getters & Setters
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

  public boolean getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(boolean aPublic) {
    isPublic = aPublic;
  }

  public String getHistory() {
    return history;
  }

  public void setHistory(String history) {
    this.history = history;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
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
}
