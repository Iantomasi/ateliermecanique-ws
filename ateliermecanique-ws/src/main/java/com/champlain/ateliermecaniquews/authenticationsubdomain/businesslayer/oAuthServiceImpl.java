package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.UserIdentifier;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.RoleRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;



import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class oAuthServiceImpl implements oAuthService{

    final private TokenService tokenService;
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;

    @Autowired
    final private RestTemplate restTemplate;


    @Override
    public User googleLogin(String JWT) throws ParseException, JOSEException {
        String validation = tokenService.verifyGoogleToken(JWT);

        if (validation.equals("Token is valid and not expired.")) {
            String[] tokenParts = JWT.split("\\.");

            String encodedBody = tokenParts[1];
            String decodedBody = new String(Base64.getDecoder().decode(encodedBody));

            JSONObject tokenBody = new JSONObject(decodedBody);
            String email = tokenBody.optString("email");

            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {

                User customerAccount = optionalUser.get();
                return customerAccount;

            } else {
                // User not found, create a new account
                String firstName = tokenBody.optString("given_name");
                String lastName = tokenBody.optString("family_name");
                String picture = tokenBody.optString("picture");

                Set<Role> roles = new HashSet<>();
                Role role = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));

                roles.add(role);

                // Build the request model for creating a new account
                User user = User.builder()
                        .userIdentifier(new UserIdentifier())
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .roles(roles)
                        .picture(picture)
                        .build();

                userRepository.save(user);

                // Return the response model for the newly created user
                return user;
            }
        } else {
            throw new NullPointerException(validation);
        }
    }


    @Override
    public User facebookLogin(String token) {
        String validation = tokenService.verifyFacebookToken(token);

        if (validation.equals("Token is valid and not expired.")) {
            String facebookUserProfileUrl = "https://graph.facebook.com/v12.0/me?fields=email,first_name,last_name,picture&access_token=" + token;

            try {
                // Making a request to the Facebook API to get user information
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        facebookUserProfileUrl,
                        HttpMethod.GET,
                        null,
                        String.class
                );

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String responseBody = responseEntity.getBody();
                    JSONObject userData = new JSONObject(responseBody);

                    // Extracting required information from the Facebook response
                    String email = userData.optString("email");
                    String firstName = userData.optString("first_name");
                    String lastName = userData.optString("last_name");

                    JSONObject pictureData = userData.optJSONObject("picture").optJSONObject("data");
                    String pictureUrl = pictureData.optString("url");

                    Optional<User> optionalUser = userRepository.findByEmail(email);

                    if (optionalUser.isPresent()) {
                        // User already exists, return the existing user
                        return optionalUser.get();
                    } else {

                        // User not found, create a new account
                        Set<Role> roles = new HashSet<>();
                        Role role = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(role);


                        User user = User.builder()
                                .userIdentifier(new UserIdentifier())
                                .email(email)
                                .firstName(firstName)
                                .lastName(lastName)
                                .roles(roles)
                                .picture(pictureUrl)
                                .build();

                        userRepository.save(user);


                        return user;
                    }
                }
            } catch (Exception e) {
                // Handle exception appropriately
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException(validation);
        }

        return null; // Handle this case according to your requirements
    }


//    @Override
//    public CustomerAccountResponseModel instagramLogin(LoginRequestModel loginRequestModel) {
//        String validation = tokenService.verifyInstagramToken(loginRequestModel.getToken());
//
//        if(validation.equals("Token is valid and not expired.")){
//
//            User account = userRepository.findByEmail(loginRequestModel.getEmail())
//                    .orElseThrow(()-> new UsernameNotFoundException("User not found with email: "+loginRequestModel.getEmail()));;
//
//            if(account == null){
//                CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel = CustomerAccountoAuthRequestModel.builder()
//                        .email(loginRequestModel.getEmail())
//                        .firstName(loginRequestModel.getFirstName())
//                        .lastName(loginRequestModel.getLastName())
//                        .token(loginRequestModel.getToken())
//                        .role(ERole.ROLE_CUSTOMER.name())
//                        .build();
//                return customerAccountService.createCustomerAccountForoAuth(customerAccountoAuthRequestModel);
//            }
//            else {
//              return customerAccountResponseMapper.entityToResponseModel(account);
//            }
//        }
//        else {
//            throw new NullPointerException(validation);
//        }
//    }



}
