package com.task.todo.repositories;

import com.task.todo.models.ApplicationSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends CrudRepository<ApplicationSetting, Integer> {
  //TODO create custom query to get the first setting
}
