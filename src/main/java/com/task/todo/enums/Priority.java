package com.task.todo.enums;

import java.util.HashMap;
import java.util.Map;

public enum Priority {
  MUST("Must"),
  HIGH("High"),
  MEDIUM("Medium"),
  LOW("Low"),
  NONE("None");

  private final String displayName;

  Priority(final String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return this.displayName;
  }

  //region Reverse lookup
  private static final Map<String, Priority> lookup = new HashMap<>();

  static {
    for (Priority priority : Priority.values()) {
      lookup.put(priority.displayName, priority);
    }
  }

  public static Priority getByDisplayName(String displayName) {
    return lookup.get(displayName);
  }
  //endregion
}