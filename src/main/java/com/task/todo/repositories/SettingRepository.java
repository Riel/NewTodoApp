package com.task.todo.repositories;

import com.task.todo.models.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends CrudRepository<Setting, Long> {

  //TODO create custom query to get the first setting
}
