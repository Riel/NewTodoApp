package com.task.todo.services;

import com.task.todo.enums.Status;
import com.task.todo.exceptions.ContextDoesNotExistException;
import com.task.todo.exceptions.ProjectDoesNotExistException;
import com.task.todo.exceptions.UserDoesNotExistException;
import com.task.todo.models.Context;
import com.task.todo.models.Project;
import com.task.todo.models.Todo;
import com.task.todo.models.User;
import com.task.todo.repositories.ContextRepository;
import com.task.todo.repositories.ProjectRepository;
import com.task.todo.repositories.UserRepository;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

  @PersistenceContext
  EntityManager entityManager;

  private UserRepository userRepository;
  private ProjectRepository projectRepository;
  private ContextRepository contextRepository;


  private final String ALL_FILTER = "Select all";
  private final String NOT_SET = "not set";
  private final String EMPTY_FILTER = "0";

  // TODO: remove this
  // can have this input when no data is set in the settings
  // make sure one cannot create a t_o_d_o that way
  private final String NULL_FILTER = "null";

  @Autowired
  public QueryService(UserRepository userRepository,
                      ProjectRepository projectRepository,
                      ContextRepository contextRepository) {
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
    this.contextRepository = contextRepository;
  }

  public Query buildFilterQuery(String assigneeName, String projectName, String contextName){
    Integer assigneeId = getAssigneeId(assigneeName);
    Integer projectId = getProjectId(projectName);
    Integer contextId = getContextId(contextName);

    String queryString = "SELECT * FROM todos";
    boolean hasFilter = false;

    if (assigneeId != null) {
      queryString = queryString + " WHERE " + getFilterString("assignee_id", assigneeId);
      hasFilter = true;
    }

    if (projectId != null){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + getFilterString("project_id", projectId);
      hasFilter = true;
    }

    if (contextId != null){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + getFilterString("context_id", contextId);
      hasFilter = true;
    }

    //if (!getSettingById(1L).isShowDone()){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + "status <> " + Status.FINISHED.ordinal() ;
    //}

    queryString = queryString + getOrderString();

    return entityManager.createNativeQuery(queryString , Todo.class);
  }

  private Integer getContextId(String contextName) {
    Integer contextId = null;
    if(needsToFilterFor(contextName)) {
      Context context = contextRepository.findByName(contextName)
          .orElseThrow(() -> new ContextDoesNotExistException(contextName));
      contextId = context.getId();
    }
    return contextId;
  }

  private Integer getProjectId(String projectName) {
    Integer projectId = null;
    if(needsToFilterFor(projectName)){
      Project project = projectRepository.findByName(projectName)
          .orElseThrow(() -> new ProjectDoesNotExistException(projectName));
      projectId = project.getId();
    }
    return projectId;
  }

  private Integer getAssigneeId(String assigneeName) {
    Integer assigneeId = null;
    if(needsToFilterFor(assigneeName)){
      User assignee = userRepository.findFirstByUsername(assigneeName)
          .orElseThrow(() -> new UserDoesNotExistException(assigneeName));
      assigneeId = (int)assignee.getId();
    }
    return assigneeId;
  }

  private String getFilterString(String field, Integer match){
    return field + " in ( '" + match + "', '" + NOT_SET + "') ";
  }

  private boolean needsToFilterFor(String parameter) {
    return parameter != null && !anyFilterEquals(parameter);
  }

  private boolean anyFilterEquals(String parameter){
    return NULL_FILTER.equals(parameter) || EMPTY_FILTER.equals(parameter) || ALL_FILTER.equals(parameter);
  }

  public String getAllFilter() {
    return ALL_FILTER;
  }

  private String getOrderString(){
    return " ORDER BY CASE WHEN due_date IS NULL THEN 1 ELSE 0 END, due_date, priority;";
  }
}
