package com.task.todo.enums;

import java.util.HashMap;
import java.util.Map;

public enum Status {
  NOT_STARTED("Not started"),
  PROGRESS("In progress"),
  ON_HOLD("On hold"),
  BLOCKED("Blocked"),
  FINISHED("Done");

  private final String displayName;

  Status(final String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return this.displayName;
  }

  //region Reverse lookup
  private static final Map<String, Status> lookup = new HashMap<>();

  static {
    for (Status status : Status.values()) {
      lookup.put(status.displayName, status);
    }
  }

  public static Status getByDisplayName(String displayName) {
    return lookup.get(displayName);
  }
  //endregion
}
