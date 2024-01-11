package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.LoginRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;


import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.CustomerAccountoAuthRequestModel;
import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
public class oAuthServiceImpl implements oAuthService{

    final private TokenService tokenService;
    final private UserRepository userRepository;
    final private CustomerAccountService customerAccountService;
    final private CustomerAccountResponseMapper customerAccountResponseMapper;
    @Override
    public CustomerAccountResponseModel googleLogin(String JWT) throws ParseException, JOSEException {
        String validation = tokenService.verifyGoogleToken(JWT);


        if (validation.equals("Token is valid and not expired.")){
            String[] tokenParts = JWT.split("\\.");

            String encodedBody = tokenParts[1];
            String decodedBody = new String(Base64.getDecoder().decode(encodedBody));

            JSONObject tokenBody = new JSONObject(decodedBody);
            String email = tokenBody.optString("email");

            User customerAccount = userRepository.findByEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("User not found with email: "+email));

            if(customerAccount == null){
                String firstName = tokenBody.optString("given_name");
                String lastName =tokenBody.optString("family_name");
                CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel = CustomerAccountoAuthRequestModel.builder()
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .token(JWT)
                        .role(ERole.ROLE_CUSTOMER.name())
                        .build();
               return customerAccountService.createCustomerAccountForoAuth(customerAccountoAuthRequestModel);
            }
            else {
                return customerAccountResponseMapper.entityToResponseModel(customerAccount);
            }
        }
        else {
            throw new NullPointerException(validation);
        }
    }

    @Override
    public CustomerAccountResponseModel facebookLogin(LoginRequestModel loginRequestModel) {
        String validation = tokenService.verifyFacebookToken(loginRequestModel.getToken());

        if(validation.equals("Token is valid and not expired.")){

            User account = userRepository.findByEmail(loginRequestModel.getEmail())
                    .orElseThrow(()-> new UsernameNotFoundException("User not found with email: "+loginRequestModel.getEmail()));

            if(account == null){
                CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel = CustomerAccountoAuthRequestModel.builder()
                        .email(loginRequestModel.getEmail())
                        .firstName(loginRequestModel.getFirstName())
                        .lastName(loginRequestModel.getLastName())
                        .token(loginRequestModel.getToken())
                        .role(ERole.ROLE_CUSTOMER.name())
                        .build();
                return customerAccountService.createCustomerAccountForoAuth(customerAccountoAuthRequestModel);
            }
            else {
                return customerAccountResponseMapper.entityToResponseModel(account);
            }
        }
        else {
            throw new NullPointerException(validation);
        }
    }

    @Override
    public CustomerAccountResponseModel instagramLogin(LoginRequestModel loginRequestModel) {
        String validation = tokenService.verifyInstagramToken(loginRequestModel.getToken());

        if(validation.equals("Token is valid and not expired.")){

            User account = userRepository.findByEmail(loginRequestModel.getEmail())
                    .orElseThrow(()-> new UsernameNotFoundException("User not found with email: "+loginRequestModel.getEmail()));;

            if(account == null){
                CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel = CustomerAccountoAuthRequestModel.builder()
                        .email(loginRequestModel.getEmail())
                        .firstName(loginRequestModel.getFirstName())
                        .lastName(loginRequestModel.getLastName())
                        .token(loginRequestModel.getToken())
                        .role(ERole.ROLE_CUSTOMER.name())
                        .build();
                return customerAccountService.createCustomerAccountForoAuth(customerAccountoAuthRequestModel);
            }
            else {
              return customerAccountResponseMapper.entityToResponseModel(account);
            }
        }
        else {
            throw new NullPointerException(validation);
        }
    }



}
