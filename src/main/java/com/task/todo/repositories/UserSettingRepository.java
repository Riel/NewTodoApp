package com.task.todo.repositories;

import com.task.todo.models.User;
import com.task.todo.models.UserSetting;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingRepository extends CrudRepository<UserSetting, Long> {
  Optional<UserSetting> findByUser(User user);
}
