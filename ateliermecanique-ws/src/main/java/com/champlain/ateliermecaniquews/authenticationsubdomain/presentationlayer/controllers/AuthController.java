package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.controllers;

import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.oAuthService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.UserIdentifier;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.RoleRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.LoginRequest;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.ResetPasswordEmailRequest;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.ResetPasswordRequest;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.SignupRequest;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Response.JWTResponse;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Response.MessageResponse;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.jwt.JwtUtils;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import com.champlain.ateliermecaniquews.emailsubdomain.businesslayer.EmailService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {

    final private oAuthService oAuthService;
    final private RestTemplate restTemplate;
    @Autowired
    final private UserRepository userRepository;

    @Autowired
    final private AuthenticationManager authenticationManager;

    @Autowired
    final private RoleRepository roleRepository;

    @Autowired
    final private PasswordEncoder encoder;

    @Autowired
    final private JwtUtils jwtUtils;

    @Autowired
    final private UserDetailsService userDetailsService;

    final private EmailService emailService;



    @PostMapping("/google-login/{JWT}")
    public ResponseEntity<JWTResponse> googleLogin(@PathVariable String JWT) {
        try {
            User user = oAuthService.googleLogin(JWT);
            Map<String, String> parameters = new HashMap<>();
            parameters.put("customerName", user.getFirstName()+" "+user.getLastName());

            emailService.sendEmail(user.getEmail(),"Account Registration","registration.html",parameters);
            return generateResponse(user);
        } catch (JOSEException | ParseException | MessagingException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping("/facebook-login/{token}")
    public ResponseEntity<JWTResponse> facebookToken(@PathVariable String token) throws MessagingException {
        User user = oAuthService.facebookLogin(token);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerName", user.getFirstName()+" "+user.getLastName());

        emailService.sendEmail(user.getEmail(),"Account Registration","registration.html",parameters);
        return generateResponse(user);
    }

//
//
//
//    @PostMapping("/instagram-login")
//    public ResponseEntity<?> instagramLogin(@RequestBody LoginRequestModel loginRequestModel){
//        return ResponseEntity.ok().body(oAuthService.instagramLogin(loginRequestModel));
//    }


    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JWTResponse(jwt,
                userDetails.getUserId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getPhoneNumber(),
                userDetails.getEmail(),
                null,
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) throws MessagingException {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: email is already in use!"));
        }

        User user = User.builder()
                .userIdentifier(new UserIdentifier())
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .phoneNumber(signupRequest.getPhoneNumber())
                .email(signupRequest.getEmail())
                .password(encoder.encode(signupRequest.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();


        Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerName", user.getFirstName()+" "+user.getLastName());

        emailService.sendEmail(user.getEmail(),"Account Registration","registration.html",parameters);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody ResetPasswordEmailRequest emailRequest) throws Exception {
        if(userRepository.existsByEmail(emailRequest.getEmail())){
            User user = userRepository.findUserByEmail(emailRequest.getEmail());

            String jwt = jwtUtils.generateJwtResponseForOAuth(emailRequest.getEmail());

            Map<String, String> parameters = new HashMap<>();
            parameters.put("token",jwt);

            emailService.sendEmail(user.getEmail(),"Password Reset","passwordReset.html",parameters);

            return ResponseEntity.ok().build();
        } else {
            String message = "No account exists with email: " + emailRequest.getEmail();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String email = userDetails.getEmail();

        User user = userRepository.findUserByEmail(email);
        if(user == null){
            String message = "No account exists with email: " + email;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        user.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok().build();

    }


    private ResponseEntity<JWTResponse> generateResponse(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtResponseForOAuth(user.getEmail());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new JWTResponse(jwt,
                user.getUserIdentifier().getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPicture(),
                roles
        ));
    }
}


