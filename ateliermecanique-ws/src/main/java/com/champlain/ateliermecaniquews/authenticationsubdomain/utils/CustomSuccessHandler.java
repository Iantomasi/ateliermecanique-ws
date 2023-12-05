//package com.champlain.ateliermecaniquews.authenticationsubdomain.utils;
//
//import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.UserService;
//import com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer.UserRepository;
//
//import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.RegisterDTO;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    UserRepository userRepo;
//
//    @Autowired
//    UserService userService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
//            DefaultOAuth2User  userDetails = (DefaultOAuth2User ) authentication.getPrincipal();
//            String username = userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"@gmail.com" ;
//            if(userRepo.findByEmail(username) == null) {
//                RegisterDTO user = new RegisterDTO();
//                user.setEmail(username);
//                user.setUsername(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
//                user.setPassword(("Dummy"));
//                user.setRole("USER");
//                userService.saveNewUser(user);
//            }
//        }
//    }
//}
