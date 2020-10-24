package com.task.todo.models;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "todos")
public class Todo implements Comparable<Todo> {

  //region Fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private String link;

  @ManyToOne (cascade = CascadeType.PERSIST)
  private Project project;

  @ManyToOne (cascade = CascadeType.PERSIST)
  private Context context;

  private Priority priority;
  private Status status;
  private boolean isDeleted;
  private boolean isPublic;
  private String history;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate creationDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dueDate;

  private LocalDate completionDate;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private User creator;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private User assignee;
  //endregion


  public Todo() {
    creationDate = LocalDate.now();
  }

  public Todo(String title, String description, Project project, Context context, Priority priority, Status status, LocalDate dueDate, User assignee) {
    this();
    this.title = title;
    this.description = description;
    this.project = project;
    this.context = context;
    this.dueDate = dueDate;
    this.priority = priority;
    this.status = status;
    this.assignee = assignee;
  }


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

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context context) {
    this.context = context;
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

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public User getAssignee() {
    return assignee;
  }

  public void setAssignee(User owner) {
    this.assignee = owner;
  }
  //endregion


  public boolean hasLink(){
    return link != null;
  }

  @Override
  public int compareTo(Todo otherTodo) {
    if (priority.ordinal() < otherTodo.getPriority().ordinal()) {
      return -1;
    } else if (priority.ordinal() == otherTodo.getPriority().ordinal()) {
      return 0;
    } else {
      return 1;
    }
  }
}