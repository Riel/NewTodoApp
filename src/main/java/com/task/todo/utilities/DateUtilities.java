package com.task.todo.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtilities {

  private final static String DATE_FORMAT = "yyyy-MM-dd";

  public static String localDateToString(LocalDate date){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return formatter.format(date);
  }
}
