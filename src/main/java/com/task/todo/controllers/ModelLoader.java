package com.task.todo.controllers;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Todo;
import com.task.todo.models.User;
import com.task.todo.services.SettingServiceImpl;
import org.springframework.ui.Model;

public class ModelLoader {

  public static void addTodoAttributes(Model model, Iterable<User> users, SettingServiceImpl settingService, Todo todo) {
    model.addAttribute("todo", todo);
    model.addAttribute("owners", users);
    model.addAttribute("priorities", Priority.values());
    model.addAttribute("statuses", Status.values());
    model.addAttribute("contexts", settingService.getContexts());
    model.addAttribute("projects", settingService.getProjects());
  }
}
