package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.controllers;

import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.TokenService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.oAuthService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.RoleRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.LoginRequest;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.LoginRequestModel;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.SignupRequest;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Response.JWTResponse;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Response.MessageResponse;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.jwt.JwtUtils;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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




    @PostMapping("/google-login/{JWT}")
    public ResponseEntity<?> googleLogin(@PathVariable String JWT) {
        try {
            User user = oAuthService.googleLogin(JWT);

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtResponseForOAuth(user.getEmail());

            // Extract roles from user roles and convert to a list of strings
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            // Include JWTResponse in the response
            return ResponseEntity.ok().body(new JWTResponse(jwt,
                    user.getUserIdentifier().getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getPicture(),
                    roles
            ));
        } catch (JOSEException | ParseException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

//    @PostMapping("/facebook-login")
//    public ResponseEntity<?> facebookToken(@RequestBody LoginRequestModel loginRequestModel){
//            return ResponseEntity.ok().body(oAuthService.facebookLogin(loginRequestModel));
//    }
//
//
//
//    @PostMapping("/instagram-login")
//    public ResponseEntity<?> instagramLogin(@RequestBody LoginRequestModel loginRequestModel){
//        return ResponseEntity.ok().body(oAuthService.instagramLogin(loginRequestModel));
//    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){


        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: email is already in use!"));
        }

        User user = User.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .phoneNumber(signupRequest.getPhoneNumber())
                .email(signupRequest.getEmail())
                .password(encoder.encode(signupRequest.getPassword())).build();

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();


        if(strRoles == null){
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role ->{

                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }
}


