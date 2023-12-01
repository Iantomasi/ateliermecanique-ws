package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer;


import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("customer")
    public RegisterDTO userRegistrationDto() {
        return new RegisterDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "signup";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("customer")
                                      RegisterDTO registrationDto) {
        userService.saveNewUser(registrationDto);
        return "redirect:/login";
    }
}
