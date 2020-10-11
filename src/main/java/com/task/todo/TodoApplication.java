package com.task.todo;

import com.task.todo.enums.Priority;
import com.task.todo.enums.Status;
import com.task.todo.models.Context;
import com.task.todo.models.Project;
import com.task.todo.models.User;
import com.task.todo.models.Setting;
import com.task.todo.models.Todo;
import com.task.todo.repositories.SettingRepository;
import com.task.todo.repositories.UserRepository;
import com.task.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.TimeZone;

@SpringBootApplication
public class TodoApplication implements CommandLineRunner {

  // TODO: remove these
  private UserRepository userRepository;
  private SettingRepository settingRepository;

  @Autowired
  public TodoApplication(UserRepository userRepository, SettingRepository settingRepository) {
    this.userRepository = userRepository;
    this.settingRepository = settingRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }

  @Override
  public void run(String... args) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    // initializeDatabase();
  }

  private void initializeDatabase(){
    User riel = new User("Riel", "riel@riel.hu", "r");
    User nelita = new User("Nelita", "nelita@nelita.hu", "n");

    String p1 = "GFA";
    String p2 = "Home";
    String p3 = "Move to Hungary";
    String p4 = "Todo app";

    String c1 = "Computer";
    String c2 = "Home";
    String c3 = "Workplace";
    String c4 = "Phone";

    for (int j = 0; j < 20; j++) {
      for (int i = 0; i < 4; i++) {
        String name = "Name for id " + i;
        String desc = "Description for the task: " + name;
        String project;
        String context;
        Priority prio;
        Status stat;
        User o;
        if (i == 0){
          project = p1;
          context = c1;
          prio = Priority.MEDIUM;
          stat = Status.BLOCKED;
          o = riel;
        } else if (i == 1){
          project = p2;
          context = c2;
          prio = Priority.HIGH;
          stat = Status.NOT_STARTED;
          o = nelita;
        } else if (i == 2){
          project = p3;
          context = c3;
          prio = Priority.MUST;
          stat = Status.PROGRESS;
          o = riel;
        } else {
          project = p4;
          context = c4;
          prio = Priority.LOW;
          stat = Status.ON_HOLD;
          o = nelita;
        }

        LocalDate due = LocalDate.now();
        Todo t = new Todo(name, desc, new Project(project), new Context(context), prio, stat, due, o);
        o.addTodo(t);
      }
    }

    userRepository.save(riel);
    userRepository.save(nelita);

    Setting s = new Setting();
    s.addContext(new Context(c1));
    s.addContext(new Context(c2));
    s.addContext(new Context(c3));
    s.addContext(new Context(c4));

    s.addProject(new Project(p1));
    s.addProject(new Project(p2));
    s.addProject(new Project(p3));
    s.addProject(new Project(p4));

    settingRepository.save(s);
  }
}