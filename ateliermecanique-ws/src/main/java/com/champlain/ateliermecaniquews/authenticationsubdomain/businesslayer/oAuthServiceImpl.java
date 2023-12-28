package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.Role;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountoAuthRequestModel;
import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
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
        if (validation != "Token signature is valid."){
            throw new NullPointerException(validation);
        }
        String[] tokenParts = JWT.split("\\.");

        String encodedBody = tokenParts[1];
        String decodedBody = new String(Base64.getDecoder().decode(encodedBody));

        JSONObject tokenBody = new JSONObject(decodedBody);
        String exp = tokenBody.optString("exp"); //expiration date in Unix code

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
                    .roles(Role.CUSTOMER)
                    .build();
           return customerAccountService.createCustomerAccountForoAuth(customerAccountoAuthRequestModel);
        }
        else {
            return customerAccountResponseMapper.entityToResponseModel(customerAccount);
        }
    }
}
