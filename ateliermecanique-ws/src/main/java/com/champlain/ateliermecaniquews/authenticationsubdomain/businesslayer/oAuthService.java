package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.LoginRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface oAuthService {
    CustomerAccountResponseModel googleLogin(String JWT) throws ParseException, JOSEException;
    CustomerAccountResponseModel facebookLogin(LoginRequestModel loginRequestModel);
}
