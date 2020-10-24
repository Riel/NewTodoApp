package com.task.todo.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  public String handleGlobalSettingDoesNotExistException(GlobalSettingDoesNotExistException e) {
    //GlobalSetting does not exist with given id : e.getMessage()
    return "";
  }

  @ExceptionHandler
  public String handleUserDoesNotExistException(UserDoesNotExistException e) {
    //User does not exist with given id/name : e.getMessage()
    return "";
  }

  @ExceptionHandler
  public String handleUserDoesNotExistException(TodoDoesNotExistException e) {
    //Todo does not exist with given id/name : e.getMessage()
    return "";
  }

  @ExceptionHandler
  public String handleAuthenticationException(PrincipalIsNotTodoUserDetailsException e) {
    //Principal is not instance of AtriumUserDetails.
    return "";
  }

  @ExceptionHandler
  public String handleUsernameNotFoundException(UsernameNotFoundException e) {
    //e.getMessage()
    return "";
  }

  @ExceptionHandler
  public String handleVerificationTokenDoesNotExistException(VerificationTokenDoesNotExistException e) {
    //Verification token does not exist: + e.getMessage()
    return "";
  }

  @ExceptionHandler
  public String handleExpiredVerificationTokenException(ExpiredVerificationTokenException e) {
    //Email verification link has expired. We'll send another for your email: " + e.getMessage()
    return "";
  }

  @ExceptionHandler
  public String handleUserHasAlreadyVerifiedException(UserHasAlreadyVerifiedException e) {
    //e.getMessage() + has already verified.
    return "";
  }


  @ExceptionHandler
  public String handleEnumConversionFailedException(EnumConversionFailedException e) {
    //e.getMessage()
    return "";
  }

  @ExceptionHandler
  public String handleUnexpectedEnumValueException(UnexpectedEnumValueException e) {
    //e.getMessage()
    return "";
  }
}
