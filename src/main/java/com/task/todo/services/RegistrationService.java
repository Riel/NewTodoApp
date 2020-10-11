package com.task.todo.services;


import com.task.todo.dtos.RegistrationDTO;
import com.task.todo.email.VerificationToken;
import com.task.todo.models.User;
import java.util.List;

public interface RegistrationService {
  List<String> getRegistrationError(RegistrationDTO registrationDTO);

  boolean isRegistrationValid(RegistrationDTO registrationDTO);

  User register(RegistrationDTO registrationDTO);

  VerificationToken getVerificationToken(String token);

  boolean checkVerificationTokenExpired(VerificationToken verificationToken);

  void createVerificationTokenForUser(User newUser);

  void updateVerificationTokenForUser(User user, VerificationToken oldToken);

  void verifyUser(User user);
}
