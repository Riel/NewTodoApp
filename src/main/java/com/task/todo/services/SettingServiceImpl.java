package com.task.todo.services;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Context;
import com.task.todo.models.Project;
import com.task.todo.models.Setting;
import com.task.todo.repositories.SettingRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl {

  private SettingRepository settingRepository;
  private QueryService queryService;

  @Autowired
  public SettingServiceImpl(SettingRepository settingRepository,
                            QueryService queryService) {
    this.settingRepository = settingRepository;
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
    Setting setting = getSetting();
    setting.addProject(new Project(projectName.trim()));
    saveSetting(setting);
  }

  public void addContextToSetting(String contextName){
    Setting setting = getSetting();
    setting.addContext(new Context(contextName.trim()));
    saveSetting(setting);
  }


  public void saveSetting(Setting setting) {
    settingRepository.save(setting);
  }

  public void deleteProject(String projectName) {
    Setting setting = getSetting();
    setting.getProjects().remove(projectName);
    saveSetting(setting);
  }

  public void deleteContext(String contextName) {
    Setting setting = getSetting();
    setting.getContexts().remove(contextName);
    saveSetting(setting);
  }

  //TODO: handle exceptions
  private Setting getSetting(){
    return settingRepository.findById(1L).orElseThrow(NoSuchElementException::new);
  }
}


