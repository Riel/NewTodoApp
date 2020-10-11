package com.task.todo.services;

import com.task.todo.enums.Status;
import com.task.todo.models.Todo;
import com.task.todo.models.User;
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

  private final String ALL_FILTER = "Select all";
  private final String EMPTY_FILTER = "0";

  // TODO: remove this
  // can have this input when no data is set in the settings
  // make sure one cannot create a t_o_d_o that way
  private final String NULL_FILTER = "null";

  @Autowired
  public QueryService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Query buildFilterQuery(String assigneeName, String project, String context){
    if (anyFilterEquals(assigneeName)){
      assigneeName = null;
    }

    if (anyFilterEquals(project)){
      project = null;
    }

    if (anyFilterEquals(context)){
      context = null;
    }

    String queryString = "SELECT * FROM todos";

    boolean hasFilter = false;
    if (assigneeName != null){
      Optional<User> assignee = userRepository.findFirstByUsername(assigneeName);
      if (assignee.isPresent()){
        queryString = queryString + " WHERE " + getFilterString("assignee_id", Long.toString(assignee.get().getId()));
        hasFilter = true;
      } else {
        // TODO : handle error case here
      }
    }
    if (project != null){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + getFilterString("project", project);
      hasFilter = true;
    }
    if (context != null){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + getFilterString("context", context);
      hasFilter = true;
    }
    //if (!getSettingById(1L).isShowDone()){
      queryString = queryString + (hasFilter ? " AND " : " WHERE ") + "status <> " + Status.FINISHED.ordinal() ;
    //}

    queryString = queryString + " ORDER BY CASE WHEN due_date IS NULL THEN 1 ELSE 0 END, due_date, priority;";

    return entityManager.createNativeQuery(queryString , Todo.class);
  }

  private String getFilterString(String field, String match){
    return field + " in ( '" + match + "', 'not set') ";
  }

  private boolean anyFilterEquals(String parameter){
    return NULL_FILTER.equals(parameter) || EMPTY_FILTER.equals(parameter) || ALL_FILTER.equals(parameter);
  }

  public String getAllFilter() {
    return ALL_FILTER;
  }
}
