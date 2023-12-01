package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer;




import com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class HomeController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/")
    public String displayDashboard(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer.User user = userRepo.findByEmail(userDetails.getUsername());
            model.addAttribute("userDetails", user.getUsername());
        } else {

            model.addAttribute("userDetails", "Guest");
        }

        return "landing";
    }
}


