package com.task.todo.controllers;

import com.task.todo.dtos.RegistrationDTO;
import com.task.todo.email.EmailService;
import com.task.todo.email.VerificationToken;
import com.task.todo.models.User;
import com.task.todo.services.RegistrationServiceImpl;
import com.task.todo.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class AccessController {

  private RegistrationServiceImpl registrationService;
  private UserServiceImpl userService;
  private EmailService emailService;

  // TODO: When a project is deleted clear filter for it - same with context
  // TODO: Complete registration so that user has settings
  // TODO: add "hide done" option to filtering
  // TODO: Create a side panel for filtering
  // TODO: display completition date if completed

  @Autowired
  public AccessController(RegistrationServiceImpl registrationService,
                          UserServiceImpl userService,
                          EmailService emailService) {
    this.registrationService = registrationService;
    this.userService = userService;
    this.emailService = emailService;
  }


  @RequestMapping(value = "login", method = RequestMethod.GET)
  public String displayLogin() {
    return "login";
  }

  /*
  @RequestMapping(value = "register", method = RequestMethod.GET)
  public String displayRegisterForm(Model model,
                                    @ModelAttribute RegistrationDTO registrationDTO,
                                    @RequestParam(required = false) boolean hasError) {
    model.addAttribute("error", hasError
        ? registrationService.getRegistrationError(registrationDTO)
        : "");
    model.addAttribute("registrationData", hasError
        ? registrationDTO
        : new RegistrationDTO());
    return "register";
  }

  @RequestMapping(path = "register", method = RequestMethod.POST)
  public String handleRegistrationRequest(@ModelAttribute RegistrationDTO registrationDTO,
                                          RedirectAttributes redirectAttributes) {
    // TODO: create proper error messages for all cases
    // TODO: give focus to input having error
    if (!registrationService.isRegistrationValid(registrationDTO)) {
      redirectAttributes.addFlashAttribute(registrationDTO);
      return "redirect:/register?hasError=true";
    }
    User user = registrationService.register(registrationDTO);
    emailService.sendVerificationEmail(user);
    redirectAttributes.addFlashAttribute("notifiedEmail", user.getEmail());
    return "redirect:/register";
  }*/

  @RequestMapping(path = "verify", method = RequestMethod.GET)
  public String handleVerificationRequest(@RequestParam(name = "token") String token, Model model) {
    VerificationToken verificationToken = registrationService.getVerificationToken(token);
    User user = verificationToken.getUser();
    if (!registrationService.checkVerificationTokenExpired(verificationToken)) {
      registrationService.updateVerificationTokenForUser(user, verificationToken);
      userService.saveUser(user);
      emailService.sendVerificationEmail(user);
      /*
      Todo: This line throws Thymeleaf exception without GlobalExceptionHandling
      throw new ExpiredVerificationTokenException(user.getEmail());
       */
      return "login";
    }
    registrationService.verifyUser(user);
    model.addAttribute("hasVerified", true);
    return "login";
  }
}
