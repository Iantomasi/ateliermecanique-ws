package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.ParseException;
import java.util.Base64;
import java.util.Collections;


@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final String jwkUrl = "https://www.googleapis.com/oauth2/v3/certs";

    private final String FACEBOOK_APP_SECRET = "408ed6f70a0674cfc76659eea63c3ea7";
    private final String FACEBOOK_APP_ID = "888035386206245";


    @Override
    public String verifyGoogleToken(String jwtToken) throws JOSEException, ParseException {

        String[] tokenParts = jwtToken.split("\\.");
        if (tokenParts.length < 1) {
            return "Invalid JWT format";
        }

        String encodedHeader = tokenParts[0];
        String decodedHeader = new String(Base64.getDecoder().decode(encodedHeader)); // Decoding base64url-encoded header

        JSONObject tokenHeader = new JSONObject(decodedHeader);
        String jwtKid = tokenHeader.optString("kid");

        String encodedBody = tokenParts[1];
        String decodedBody = new String(Base64.getDecoder().decode(encodedBody));

        JSONObject tokenBody = new JSONObject(decodedBody);
        String exp = tokenBody.optString("exp"); //expiration date in Unix code


        long expirationTime = Long.parseLong(exp);

        long currentTimeSeconds = System.currentTimeMillis() / 1000; // Current time in seconds

        if (expirationTime >= currentTimeSeconds) {

            if (jwtKid != null && !jwtKid.isEmpty()) {
                // Fetch Google's public keys
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                HttpEntity<String> entity = new HttpEntity<>(headers);

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(jwkUrl, HttpMethod.GET, entity, String.class);

                // Parse the public keys response and find the matching key
                String publicKeysResponse = response.getBody();
                JSONObject keysJSON = new JSONObject(publicKeysResponse);
                JSONArray keys = keysJSON.getJSONArray("keys");

                JSONObject selectedKey = null;

                for (int i = 0; i < keys.length(); i++) {
                    JSONObject key = keys.getJSONObject(i);
                    String keyKid = key.getString("kid");
                    if (jwtKid.equals(keyKid)) {
                        selectedKey = key;
                        break;
                    }
                }

                if (selectedKey != null) {
                    // Use Nimbus JOSE + JWT to verify the JWT token with the selected key
                    JWK jwk = JWK.parse(selectedKey.toString());
                    RSAKey rsaKey = RSAKey.parse(jwk.toJSONObject());
                    RSASSAVerifier verifier = new RSASSAVerifier(rsaKey);

                    JWSObject jwsObject = JWSObject.parse(jwtToken);
                    if (jwsObject.verify(verifier)) {
                        // Token signature is valid
                        return "Token signature is valid.";
                    } else {
                        // Token signature verification failed
                        return "Token signature verification failed.";
                    }
                } else {
                    // Matching key not found
                    return "Matching key for kid not found.";
                }
            } else {
                return "kid not found in token header";
            }
        }
        else {
                // Token has expired
                return "Token has expired.";
            }
    }

    @Override
    public String verifyFacebookToken(String accessToken) {
        String tokenCheckUrl = "https://graph.facebook.com/v13.0/debug_token?input_token=" + accessToken + "&access_token=" + FACEBOOK_APP_ID + "|" + FACEBOOK_APP_SECRET;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(tokenCheckUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONObject data = jsonResponse.getJSONObject("data");

            boolean isValid = data.getBoolean("is_valid");
            long expiresAt = data.getLong("expires_at");

            // Check if token is valid and not expired
            if (isValid) {
                long currentTimeSeconds = System.currentTimeMillis() / 1000;
                if (expiresAt >= currentTimeSeconds) {
                    return "Token is valid and not expired.";
                } else {
                    return "Token is valid but has expired.";
                }
            } else {
                return "Token is not valid.";
            }
        } else {
            return "Failed to validate token.";
        }
    }

    @Override
    public String verifyInstagramToken(String accessToken) {
        String userInfoUrl = "https://graph.instagram.com/me?fields=id,username&access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());

            // Perform checks based on the response
            if (jsonResponse.has("id") && jsonResponse.has("username")) {
                // Token is valid
                return "Instagram token is valid.";
            } else {
                // Token is not valid or doesn't provide necessary data
                return "Invalid Instagram token.";
            }
        } else {
            // Failed to validate token
            return "Failed to validate Instagram token.";
        }
    }


}
