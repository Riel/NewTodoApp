package com.task.todo.services;


import com.task.todo.dtos.RegistrationDTO;
import com.task.todo.email.VerificationToken;
import com.task.todo.exceptions.UserHasAlreadyVerifiedException;
import com.task.todo.exceptions.VerificationTokenDoesNotExistException;
import com.task.todo.models.User;
import com.task.todo.repositories.UserRepository;
import com.task.todo.repositories.VerificationTokenRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl {

  //region Fields
  private UserRepository userRepository;
  private PasswordEncoder encoder;
  private VerificationTokenRepository verificationTokenRepository;
  //endregion

  @Autowired
  public RegistrationServiceImpl(UserRepository userRepository,
                                 PasswordEncoder encoder,
                                 VerificationTokenRepository verificationTokenRepository) {
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.verificationTokenRepository = verificationTokenRepository;
  }

  //region Methods
  public List<String> getRegistrationError(RegistrationDTO registrationDTO) {
    List<String> errors = new ArrayList<>();
    if (!isPasswordValid(registrationDTO.getPassword())) {
      errors.add("Password is too short. Please use at least 6 characters!");
    }
    if (!isEmailValid(registrationDTO.getEmail())) {
      errors.add("Email is not correct!");
    }
    if (!isUsernameUnique(registrationDTO.getUsername())) {
      errors.add("User name is already taken. Please choose another one!");
    }
    if (!isEmailUnique(registrationDTO.getEmail())) {
      errors.add("E-mail is already taken. Please choose another one!");
    }
    return errors;
  }

  public boolean isRegistrationValid(RegistrationDTO registrationDTO) {
    return isPasswordValid(registrationDTO.getPassword())
        && isEmailValid(registrationDTO.getEmail())
        && isUsernameUnique(registrationDTO.getUsername())
        && isEmailUnique(registrationDTO.getEmail());
  }

  public User register(RegistrationDTO registrationDTO) {
    User newUser = new User(registrationDTO.getUsername(), registrationDTO.getEmail(),
        encoder.encode(registrationDTO.getPassword()), "USER");
    createVerificationTokenForUser(newUser);
    return userRepository.save(newUser);
  }

  public void createVerificationTokenForUser(User newUser) {
    newUser.setVerificationToken(new VerificationToken());
    newUser.getVerificationToken().setUser(newUser);
  }

  public void updateVerificationTokenForUser(User user, VerificationToken oldToken) {
    createVerificationTokenForUser(user);
    user.getVerificationToken().setId(oldToken.getId());
  }

  public void verifyUser(User user) {
    if (user.isVerified()) {
      throw new UserHasAlreadyVerifiedException(user.getUsername());
    }
    user.setVerified(true);
    userRepository.save(user);
  }

  public VerificationToken getVerificationToken(String token) {
    Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByToken(token);
    return optionalVerificationToken.orElseThrow(() -> new VerificationTokenDoesNotExistException(token));
  }

  public boolean checkVerificationTokenExpired(VerificationToken verificationToken) {
    return LocalDateTime.now().isBefore(verificationToken.getExpiryDate());
  }

  private boolean isEmailUnique(String email) {
    return email != null
        && !userRepository.findFirstByEmail(email).isPresent();
  }

  private boolean isUsernameUnique(String username) {
    return username != null
        && !userRepository.findFirstByUsername(username).isPresent();
  }

  private boolean isPasswordValid(String password) {
    return password != null && password.length() > 6;
  }

  private boolean isEmailValid(String email) {
    return email != null
        && email.contains("@")
        && email.contains(".")
        && email.length() > 5;
  }
  //endregion
}
