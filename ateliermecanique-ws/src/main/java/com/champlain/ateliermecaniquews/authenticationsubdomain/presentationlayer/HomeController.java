package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.TokenService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.oAuthService;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class HomeController {
    final private TokenService tokenService;
    final private oAuthService oAuthService;
    final private RestTemplate restTemplate;
    
    @GetMapping("/google-token-verification/{JWT}")
    public ResponseEntity<String> verifyGoogleToken(@PathVariable String JWT){
        try {
            return ResponseEntity.ok().body(tokenService.verifyGoogleToken(JWT));
            // Handle the verification result accordingly
        } catch (JOSEException | ParseException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/google-login/{JWT}")
    public ResponseEntity<CustomerAccountResponseModel> googleLogin(@PathVariable String JWT){
        try {
            return ResponseEntity.ok().body(oAuthService.googleLogin(JWT));
        }catch (JOSEException | ParseException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping("/facebook-token-verification/{accessToken}")
    public ResponseEntity<String> verifyFacebookToken(@PathVariable String accessToken){
        try {
            return ResponseEntity.ok().body(tokenService.verifyFacebookToken(accessToken));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/facebook-login")
    public ResponseEntity<CustomerAccountResponseModel> facebookToken(@RequestBody LoginRequestModel loginRequestModel){
            return ResponseEntity.ok().body(oAuthService.facebookLogin(loginRequestModel));
    }

    @GetMapping("/instagram-token-verification/{accessToken}")
    public ResponseEntity<String> verifyInstagramToken(@PathVariable String accessToken){
        try {
            return ResponseEntity.ok().body(tokenService.verifyInstagramToken(accessToken));
        } catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/instagram-login")
    public ResponseEntity<CustomerAccountResponseModel> instagramLogin(@RequestBody LoginRequestModel loginRequestModel){
        return ResponseEntity.ok().body(oAuthService.instagramLogin(loginRequestModel));
    }

    @GetMapping("/{accessToken}")
    public ResponseEntity<CustomerAccountResponseModel> getCustomerByToken(@PathVariable String accessToken){
        return ResponseEntity.ok().body(oAuthService.findCustomerByToken(accessToken));
    }
}


