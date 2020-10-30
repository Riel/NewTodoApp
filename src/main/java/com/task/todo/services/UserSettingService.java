package com.task.todo.services;

import com.task.todo.exceptions.UserSettingDoesNotExistException;
import com.task.todo.models.User;
import com.task.todo.models.UserSetting;
import com.task.todo.repositories.UserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingService {

  private UserServiceImpl userService;
  private UserSettingRepository settingRepository;

  @Autowired
  public UserSettingService(UserServiceImpl userService, UserSettingRepository settingRepository) {
    this.userService = userService;
    this.settingRepository = settingRepository;
  }

  public UserSetting getActiveUserSetting() {
    User user = userService.getAuthenticatedUserWithoutProperties();
    return settingRepository.findByUser(user).orElseThrow(() -> new UserSettingDoesNotExistException(user.getId()));
  }

  public void updateActiveUserSetting(String assigneeName, String projectName, String contextName) {
    UserSetting setting = getActiveUserSetting();
    setting.setLookUpAssigneeName(assigneeName);
    setting.setLookUpProjectName(projectName);
    setting.setLookUpContextname(contextName);
    settingRepository.save(setting);
  }
}
