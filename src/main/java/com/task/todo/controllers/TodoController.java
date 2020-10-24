package com.task.todo.controllers;

import com.task.todo.dtos.FullTodoDTO;
import com.task.todo.dtos.SimpleTodoDTO;
import com.task.todo.services.SettingServiceImpl;
import com.task.todo.services.TodoServiceImpl;
import com.task.todo.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

  private TodoServiceImpl todoService;
  private SettingServiceImpl settingService;
  private UserServiceImpl userService;

  @Autowired
  public TodoController(TodoServiceImpl todoService,
                        SettingServiceImpl settingService,
                        UserServiceImpl userService) {
    this.todoService = todoService;
    this.settingService = settingService;
    this.userService = userService;
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String getMainPage(Model model,
                            @RequestParam (required = false) String owner,
                            @RequestParam (required = false) String project,
                            @RequestParam (required = false) String context){

    List<SimpleTodoDTO> todoViewModels = todoService.getFilteredTodos(owner, project, context);

    model.addAttribute("todos", todoViewModels);
    model.addAttribute("owners", userService.getUserNames());
    model.addAttribute("projects", settingService.getProjectNames());
    model.addAttribute("contexts", settingService.getContextNames());

    model.addAttribute("selectedOwner", owner);
    model.addAttribute("selectedProject", project);
    model.addAttribute("selectedContext", context);
    model.addAttribute("items", todoViewModels.size());

    return "main";
  }

  @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
  public String showEditForm(Model model, @PathVariable Long id) {
    FullTodoDTO dto = todoService.getInstantOrNormalTodo(id);
    model.addAttribute("displayMode", "edit");
    ModelLoader.addTodoAttributes(model, userService.getUsers(), settingService , dto);
    return "todo";
  }

  @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
  public String updateTodo(@PathVariable Long id, @ModelAttribute FullTodoDTO dto) {
    dto.setId(id);
    todoService.saveTodo(dto);
    return "redirect:/todo/list";
  }

  @RequestMapping(path = "/add", method = RequestMethod.GET)
  public String showAddForm(Model model) {
    FullTodoDTO dto = todoService.createEmptyTodo();
    model.addAttribute("displayMode", "add");
    ModelLoader.addTodoAttributes(model, userService.getUsers(), settingService , dto);
    return "todo";
  }

  @RequestMapping(path = "/add", method = RequestMethod.POST)
  public String addTodo(@ModelAttribute FullTodoDTO dto) {
    todoService.saveTodo(dto);
    return "redirect:/todo/list";
  }

  @RequestMapping(path="/add-instant", method = RequestMethod.POST)
  public String addInstantTodo(String title, Model model){
    todoService.saveInstantTodo(title);
    FullTodoDTO dto = todoService.createEmptyTodo();
    ModelLoader.addTodoAttributes(model, userService.getUsers(), settingService , dto);
    model.addAttribute("instantTaskAdded", "ok");
    model.addAttribute("displayMode", "add");
    return "todo";
  }

  @RequestMapping(path = "/id/{id}/owner/{owner}/project/{project}/context/{context}/delete", method = RequestMethod.GET)
  public String deleteTodo(@PathVariable Long id,
                           @PathVariable String owner,
                           @PathVariable String project,
                           @PathVariable String context, Model model) {
    todoService.deleteTodoById(id);
    return getMainPage(model,owner, project,context);
  }

  @RequestMapping(path = "/id/{id}/owner/{owner}/project/{project}/context/{context}/done", method = RequestMethod.GET)
  public String completeTodo(@PathVariable Long id,
                             @PathVariable String owner,
                             @PathVariable String project,
                             @PathVariable String context, Model model) {
    todoService.completeTodo(id);
    return getMainPage(model,owner, project,context);
  }

  /*@Transactional
  @RequestMapping(path = "/settings/showdone", method = RequestMethod.POST)
  public String changeShowDone(boolean showDone){
    todoService.getSettingById(1L).setShowDone(showDone);
    return "redirect:/";
  }*/
}