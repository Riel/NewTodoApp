package com.task.todo.utilities;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Todo;
import com.task.todo.models.User;
import com.task.todo.utilities.DateUtilities;
import com.task.todo.utilities.TodoColors;
import java.time.LocalDate;
import java.time.Period;

public class TodoColorizer {

  private Todo todo;

  public TodoColorizer(Todo todo) {
    this.todo = todo;
  }


  public String getContextDisplayColor(){
    if (todo.getProject() == null){
      return TodoColors.BUTTON_RED;
    }

    return todo.getStatus().ordinal() == 4
        ? TodoColors.GRAY
        : TodoColors.WHITE;
  }

  public String getProjectDisplayColor(){
    // TODO: remove duplication
    if (todo.getProject() == null){
      return TodoColors.BUTTON_RED;
    }

    return todo.getStatus().ordinal() == 4
        ? TodoColors.GRAY
        : TodoColors.WHITE;
  }

  public String getDueDateDisplayColor(){
    LocalDate dueDate = todo.getDueDate();

    if (dueDate == null || todo.getStatus().ordinal() == 4) {
      return TodoColors.GRAY;
    }

    Integer daysToFinish = Period.between(LocalDate.now(), dueDate).getDays();

    if (daysToFinish < 0) {           // should already be done
      return TodoColors.RED;
    } else if (daysToFinish == 0) {   // must be done today
      return TodoColors.ORANGE;
    } else if (daysToFinish == 1) {   // must be done tomorrow
      return TodoColors.YELLOW;
    } else if (daysToFinish == 2) {   // must be done day after tomorrow
      return TodoColors.LIME;
    } else {                          // can be done later
      return TodoColors.GREEN;
    }
  }

  public String getPriorityDisplayClass(){
    Priority priority = todo.getPriority();

    if(priority.ordinal() == 0){
      return "must-prio";
    } else if (priority.ordinal() == 1){
      return "high-prio";
    } else if (priority.ordinal() == 2){
      return "medium-prio";
    } else if (priority.ordinal() == 3){
      return "low-prio";
    } else {
      return "none-prio";
    }
  }
}
