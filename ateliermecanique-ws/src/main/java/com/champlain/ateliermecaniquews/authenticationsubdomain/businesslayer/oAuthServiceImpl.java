package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.LoginRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.Role;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;


import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.CustomerAccountoAuthRequestModel;
import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Base64;

@Service
@AllArgsConstructor
public class oAuthServiceImpl implements oAuthService{

    final private TokenService tokenService;
    final private CustomerAccountRepository customerAccountRepository;
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

            CustomerAccount customerAccount = customerAccountRepository.findCustomerAccountByEmail(email);
            if(customerAccount == null){
                String firstName = tokenBody.optString("given_name");
                String lastName =tokenBody.optString("family_name");
                CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel = CustomerAccountoAuthRequestModel.builder()
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .token(JWT)
                        .role(String.valueOf(Role.CUSTOMER))
                        .build();
               return customerAccountService.createCustomerAccountForoAuth(customerAccountoAuthRequestModel);
            }
            else {
                return customerAccountService.updateCustomerToken(customerAccount.getCustomerAccountIdentifier().getCustomerId(),JWT);
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

            CustomerAccount customerAccount = customerAccountRepository.findCustomerAccountByEmail(loginRequestModel.getEmail());

            if(customerAccount == null){
                CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel = CustomerAccountoAuthRequestModel.builder()
                        .email(loginRequestModel.getEmail())
                        .firstName(loginRequestModel.getFirstName())
                        .lastName(loginRequestModel.getLastName())
                        .token(loginRequestModel.getToken())
                        .role(String.valueOf(Role.CUSTOMER))
                        .build();
                return customerAccountService.createCustomerAccountForoAuth(customerAccountoAuthRequestModel);
            }
            else {
                return customerAccountService.updateCustomerToken(customerAccount.getCustomerAccountIdentifier().getCustomerId(),loginRequestModel.getToken());
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

            CustomerAccount customerAccount = customerAccountRepository.findCustomerAccountByEmail(loginRequestModel.getEmail());

            if(customerAccount == null){
                CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel = CustomerAccountoAuthRequestModel.builder()
                        .email(loginRequestModel.getEmail())
                        .firstName(loginRequestModel.getFirstName())
                        .lastName(loginRequestModel.getLastName())
                        .token(loginRequestModel.getToken())
                        .role(String.valueOf(Role.CUSTOMER))
                        .build();
                return customerAccountService.createCustomerAccountForoAuth(customerAccountoAuthRequestModel);
            }
            else {
              return customerAccountService.updateCustomerToken(customerAccount.getCustomerAccountIdentifier().getCustomerId(),loginRequestModel.getToken());
            }
        }
        else {
            throw new NullPointerException(validation);
        }
    }

    @Override
    public CustomerAccountResponseModel findCustomerByToken(String token) {
        CustomerAccount customerAccount = customerAccountRepository.findCustomerAccountByToken(token);

        if(customerAccount == null){
            throw new NullPointerException("Customer not found with token: "+token);
        }
        else {
            return customerAccountResponseMapper.entityToResponseModel(customerAccount);
        }
    }

}
