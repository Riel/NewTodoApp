package com.task.todo.services;

import com.task.todo.exceptions.ContextDoesNotExistException;
import com.task.todo.exceptions.ProjectDoesNotExistException;
import com.task.todo.exceptions.UserSettingDoesNotExistException;
import com.task.todo.models.Context;
import com.task.todo.models.Project;
import com.task.todo.models.User;
import com.task.todo.models.UserSetting;
import com.task.todo.repositories.ContextRepository;
import com.task.todo.repositories.ProjectRepository;
import com.task.todo.repositories.UserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingService {

  private UserServiceImpl userService;
  private UserSettingRepository settingRepository;
  private ProjectRepository projectRepository;
  private ContextRepository contextRepository;

  @Autowired
  public UserSettingService(UserServiceImpl userService,
                            UserSettingRepository settingRepository,
                            ProjectRepository projectRepository,
                            ContextRepository contextRepository) {
    this.userService = userService;
    this.settingRepository = settingRepository;
    this.projectRepository = projectRepository;
    this.contextRepository = contextRepository;
  }


  public UserSetting getActiveUserSetting() {
    User user = userService.getAuthenticatedUserWithoutProperties();
    return settingRepository.findByUser(user).orElseThrow(() -> new UserSettingDoesNotExistException(user.getId()));
  }

  public void updateActiveUserSetting(String assigneeName, String projectName, String contextName) {
    UserSetting setting = getActiveUserSetting();

    if (canUpdate(assigneeName)){
      setting.setLookUpAssignee(userService.getUserByName(assigneeName));
    } else {
      setting.setLookUpAssignee(null);
    }

    if (canUpdate(projectName)) {
      Project project = projectRepository.findByName(projectName)
          .orElseThrow(() -> new ProjectDoesNotExistException(projectName));
      setting.setLookUpProject(project);
    } else {
      setting.setLookUpProject(null);
    }

    if (canUpdate(contextName)) {
      Context context = contextRepository.findByName(contextName)
          .orElseThrow(() -> new ContextDoesNotExistException(contextName));
      setting.setLookUpContext(context);
    } else {
      setting.setLookUpContext(null);
    }

    settingRepository.save(setting);
  }

  private boolean canUpdate(String propertyName){
    return propertyName != null && !propertyName.equals("Select all");
  }
}
