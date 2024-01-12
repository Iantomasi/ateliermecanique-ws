package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface oAuthService {
    User googleLogin(String JWT) throws ParseException, JOSEException;
//    CustomerAccountResponseModel facebookLogin(LoginRequestModel loginRequestModel);
//    CustomerAccountResponseModel instagramLogin(LoginRequestModel loginRequestModel);
}
