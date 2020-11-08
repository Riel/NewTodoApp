package com.task.todo.services;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.exceptions.ContextDoesNotExistException;
import com.task.todo.exceptions.ProjectDoesNotExistException;
import com.task.todo.models.ApplicationSetting;
import com.task.todo.models.Context;
import com.task.todo.models.Project;
import com.task.todo.repositories.ContextRepository;
import com.task.todo.repositories.ProjectRepository;
import com.task.todo.repositories.SettingRepository;
import com.task.todo.repositories.TodoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppSettingServiceImpl {

  private SettingRepository settingRepository;
  private ProjectRepository projectRepository;
  private ContextRepository contextRepository;
  private TodoRepository todoRepository;
  private QueryService queryService;

  @Autowired
  public AppSettingServiceImpl(SettingRepository settingRepository,
                               ProjectRepository projectRepository,
                               ContextRepository contextRepository,
                               TodoRepository todoRepository,
                               QueryService queryService) {
    this.settingRepository = settingRepository;
    this.projectRepository = projectRepository;
    this.contextRepository = contextRepository;
    this.todoRepository = todoRepository;
    this.queryService = queryService;
  }


  public List<String> getPriorities (){
    return Arrays.stream(Priority.values()).map(p -> p.toString()).collect(Collectors.toList());
  }

  public List<String> getStatuses (){
    return Arrays.stream(Status.values()).map(p -> p.toString()).collect(Collectors.toList());
  }


  public List<String> getProjectNames(){
    List<String> projectNames = new ArrayList<>();
    getProjects().forEach(p->projectNames.add(p));
    projectNames.add(queryService.getAllFilter());
    return projectNames;
  }

  public List<String> getContextNames(){
    List<String> contextNames = new ArrayList<>();
    getContexts().forEach(c->contextNames.add(c));
    contextNames.add(queryService.getAllFilter());
    return contextNames;
  }

  public Iterable<String> getContexts() {
    return getSetting().getContexts().stream().map(c -> c.getName()).collect(Collectors.toList());
  }

  public Iterable<String> getProjects() {
    return getSetting().getProjects().stream().map(c -> c.getName()).collect(Collectors.toList());
  }


  public void addProjectToSetting(String projectName){
    ApplicationSetting appSetting = getSetting();

    if (appSetting.getProjects().stream().filter(p -> p.getName().equals(projectName)).findFirst().isPresent()) {
      // TODO: send message here
      return;
    }

    appSetting.addProject(new Project(projectName.trim()));
    saveSetting(appSetting);
  }

  public void addContextToSetting(String contextName){
    ApplicationSetting appSetting = getSetting();

    if (appSetting.getContexts().stream().filter(c -> c.getName().equals(contextName)).findFirst().isPresent()) {
      // TODO: send message here
      return;
    }

    appSetting.addContext(new Context(contextName.trim()));
    saveSetting(appSetting);
  }


  public void saveSetting(ApplicationSetting setting) {
    settingRepository.save(setting);
  }

  public void deleteProject(String projectName) {
    ApplicationSetting appSetting = getSetting();
    Project project = appSetting.getProjects().stream()
        .filter(p -> p.getName().equals(projectName))
        .findFirst()
        .orElseThrow(() -> new ProjectDoesNotExistException(projectName));
    appSetting.getProjects().remove(project);
    // TODO: decide what to do when a project is deleted that has Todos!
    todoRepository.findAllByProject(project).stream().forEach(t -> t.setProject(null));
    projectRepository.delete(project);
    saveSetting(appSetting);
  }

  public void deleteContext(String contextName) {
    ApplicationSetting appSetting = getSetting();
    Context context = appSetting.getContexts().stream()
        .filter(c -> c.getName().equals(contextName))
        .findFirst()
        .orElseThrow(() -> new ContextDoesNotExistException(contextName));
    appSetting.getContexts().remove(context);
    // TODO: decide what to do when a context is deleted that has Todos!
    todoRepository.findAllByContext(context).stream().forEach(t -> t.setContext(null));
    contextRepository.delete(context);
    saveSetting(appSetting);
  }

  //TODO: handle exceptions
  private ApplicationSetting getSetting(){
    return settingRepository.findById(1).orElseThrow(NoSuchElementException::new);
  }
}


