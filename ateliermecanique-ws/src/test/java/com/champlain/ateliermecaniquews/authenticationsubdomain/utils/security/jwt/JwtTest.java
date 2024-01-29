package com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.jwt;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.InvalidAccessTokenException;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class JwtTest {


    @InjectMocks
    private JwtUtils jwtUtils;

    private static final String SECRET = "======================BezKoder=Spring===========================";
    private static final int EXPIRATION_MS = 86400000;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtils = new JwtUtils();
        jwtUtils.setJwtSecret(SECRET);
        jwtUtils.setJwtExpirationMs(EXPIRATION_MS);
    }


    @Test
    void testGenerateJwtToken() {
        // Arrange
        jwtUtils.setJwtSecret(SECRET);
        jwtUtils.setJwtExpirationMs(EXPIRATION_MS);

        UserDetailsImpl userDetails = new UserDetailsImpl(
                1, "someUUID", "John", "Doe", "123456789", "test@example.com", "password", getGrantedAuthorities());

        Authentication authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Act
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        // Assert
        assertNotNull(jwtToken);
        assertTrue(jwtUtils.validateJwtToken(jwtToken));
        assertEquals("test@example.com", jwtUtils.getEmailFromJwtToken(jwtToken));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return Arrays.asList(() -> "ROLE_USER");
    }


    @Test
    void testGenerateJwtResponseForOAuth() {
        // Generate JWT response for OAuth
        String jwtToken = jwtUtils.generateJwtResponseForOAuth("test@example.com");

        // Validate token
        assertNotNull(jwtToken);
        assertTrue(jwtUtils.validateJwtToken(jwtToken));
        assertEquals("test@example.com", jwtUtils.getEmailFromJwtToken(jwtToken));
    }

    @Test
    void testValidateJwtTokenWithInvalidToken() {
        // Generate an invalid JWT token
        String invalidToken = "invalid-token";

        // Validate the invalid token
        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }

    @Test
    void testGetEmailFromJwtToken() {
        // Generate a JWT token
        String jwtToken = jwtUtils.generateJwtResponseForOAuth("test@example.com");

        // Get email from the token
        String email = jwtUtils.getEmailFromJwtToken(jwtToken);

        // Validate the email
        assertEquals("test@example.com", email);
    }

    @Test
    void testGetEmailFromJwtTokenWithInvalidToken() {
        // Generate an invalid JWT token
        String invalidToken = "invalid-token";

        // Get email from the invalid token
        assertThrows(MalformedJwtException.class, () -> jwtUtils.getEmailFromJwtToken(invalidToken));
    }

    @Test
    void testExceptionMessage() {
        // Arrange
        String expectedMessage = "Invalid access token";

        // Act
        InvalidAccessTokenException exception = new InvalidAccessTokenException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testThrowException() {
        // Arrange
        String expectedMessage = "Invalid access token";

        // Act and Assert
        assertThrows(InvalidAccessTokenException.class, () -> {
            throw new InvalidAccessTokenException(expectedMessage);
        });
    }

}
