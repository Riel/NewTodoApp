package com.task.todo.controllers;

import com.task.todo.dtos.FullTodoDTO;
import com.task.todo.dtos.SimpleTodoDTO;
import com.task.todo.models.UserSetting;
import com.task.todo.services.AppSettingServiceImpl;
import com.task.todo.services.TodoServiceImpl;
import com.task.todo.services.UserServiceImpl;
import com.task.todo.services.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

  private TodoServiceImpl todoService;
  private AppSettingServiceImpl settingService;
  private UserServiceImpl userService;
  private UserSettingService userSettingService;

  @Autowired
  public TodoController(TodoServiceImpl todoService,
                        AppSettingServiceImpl settingService,
                        UserServiceImpl userService,
                        UserSettingService userSettingService) {
    this.todoService = todoService;
    this.settingService = settingService;
    this.userService = userService;
    this.userSettingService = userSettingService;
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String getMainPage(Model model){

    UserSetting userSetting = userSettingService.getActiveUserSetting();
    return getFilteredMainPage(model, userSetting.getLookupAssingeeName(),
        userSetting.getLookupProjectName(), userSetting.getLookupContextName());
  }

  @RequestMapping(value = "/filteredlist", method = RequestMethod.GET)
  public String getFilteredMainPage(Model model,
                            @RequestParam (required = false) String ownerName,
                            @RequestParam (required = false) String projectName,
                            @RequestParam (required = false) String contextName){

    userSettingService.updateActiveUserSetting(ownerName, projectName, contextName);

    List<SimpleTodoDTO> filteredTodos = todoService.getFilteredTodos(ownerName, projectName, contextName);

    model.addAttribute("todos", filteredTodos);
    model.addAttribute("owners", userService.getUserNames());
    model.addAttribute("projects", settingService.getProjectNames());
    model.addAttribute("contexts", settingService.getContextNames());

    model.addAttribute("selectedOwner", ownerName);
    model.addAttribute("selectedProject", projectName);
    model.addAttribute("selectedContext", contextName);
    model.addAttribute("items", filteredTodos.size());

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
    FullTodoDTO dto = todoService.createInstantTodoDto();
    model.addAttribute("displayMode", "add");
    ModelLoader.addTodoAttributes(model, userService.getUsers(), settingService , dto);
    return "todo";
  }

  @RequestMapping(path = "/add", method = RequestMethod.POST)
  public String addTodo(@ModelAttribute FullTodoDTO dto) {
    todoService.saveNewTodo(dto);
    return "redirect:/todo/list";
  }

  @RequestMapping(path="/add-instant", method = RequestMethod.POST)
  public String addInstantTodo(String title, Model model){
    todoService.saveInstantTodo(title);
    FullTodoDTO dto = todoService.createInstantTodoDto();
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
    return getFilteredMainPage(model,owner, project,context);
  }

  @RequestMapping(path = "/id/{id}/owner/{owner}/project/{project}/context/{context}/done", method = RequestMethod.GET)
  public String completeTodo(@PathVariable Long id,
                             @PathVariable String owner,
                             @PathVariable String project,
                             @PathVariable String context, Model model) {
    todoService.completeTodo(id);
    return getFilteredMainPage(model,owner, project,context);
  }

  /*@Transactional
  @RequestMapping(path = "/settings/showdone", method = RequestMethod.POST)
  public String changeShowDone(boolean showDone){
    todoService.getSettingById(1L).setShowDone(showDone);
    return "redirect:/";
  }*/
}