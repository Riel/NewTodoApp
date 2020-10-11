package com.task.todo.controllers;

import com.task.todo.services.SettingServiceImpl;
import com.task.todo.services.UserServiceImpl;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/settings")
public class SettingController {

  private SettingServiceImpl settingService;
  private UserServiceImpl userService;

  @Autowired
  public SettingController(SettingServiceImpl settingService,
                           UserServiceImpl userService) {
    this.settingService = settingService;
    this.userService = userService;
  }

  @RequestMapping(path = "", method = RequestMethod.GET)
  public String displaySettings(Model model) {
    model.addAttribute("owners", userService.getUserNames());
    model.addAttribute("projects", settingService.getProjects());
    model.addAttribute("contexts", settingService.getContexts());
    return "settings";
  }

  @RequestMapping(path = "/project", method = RequestMethod.POST)
  public String addProject(@RequestParam String projectName){
    settingService.addProjectToSetting(projectName);
    return "redirect:/settings";
  }

  @RequestMapping(path = "/context", method = RequestMethod.POST)
  public String addContext(@RequestParam String contextName){
    settingService.addContextToSetting(contextName);
    return "redirect:/settings";
  }

  @Transactional
  @RequestMapping(path = "/owners/{owner}", method = RequestMethod.POST)
  public String deleteUser(@PathVariable String owner){
    userService.deleteUser(owner);
    return "redirect:/settings";
  }

  @Transactional
  @RequestMapping(path = "/projects/{project}/delete", method = RequestMethod.POST)
  public String deleteProject(@PathVariable String project){
    settingService.deleteProject(project);
    return "redirect:/settings";
  }

  @Transactional
  @RequestMapping(path = "/contexts/{context}/delete", method = RequestMethod.POST)
  public String deleteContext(@PathVariable String context){
    settingService.deleteContext(context);
    return "redirect:/settings";
  }
}
