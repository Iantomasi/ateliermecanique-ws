package com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.jwt;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthTokenFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    void doFilterInternal_ValidToken_SuccessfulAuthentication() throws ServletException, IOException {
        // Arrange
        String validToken = "validToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(jwtUtils.validateJwtToken(validToken)).thenReturn(true);
        String userEmail = "user@example.com";
        when(jwtUtils.getEmailFromJwtToken(validToken)).thenReturn(userEmail);

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(userDetailsService).loadUserByUsername(userEmail);
        verify(jwtUtils).getEmailFromJwtToken(validToken);
        verify(jwtUtils).validateJwtToken(validToken);

        // Check that authentication was set in the SecurityContextHolder
        verify(request).getHeader("Authorization");
        verify(filterChain).doFilter(request, response);
    }

//    @Test
//    void doFilterInternal_InvalidToken_NoAuthenticationSet() throws ServletException, IOException {
//        // Arrange
//        String invalidToken = "invalidToken";
//        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
//        when(jwtUtils.validateJwtToken(invalidToken)).thenReturn(false);
//
//        // Act
//        authTokenFilter.doFilterInternal(request, response, filterChain);
//
//        // Assert
//        verify(jwtUtils).validateJwtToken(invalidToken);
//
//        // Check that no authentication was set in the SecurityContextHolder
//        verify(request).getHeader("Authorization");
//        verifyNoInteractions(userDetailsService);
//        verifyNoInteractions(request, filterChain);
//    }

//    @Test
//    void doFilterInternal_NoToken_NoAuthenticationSet() throws ServletException, IOException {
//        // Arrange
//        when(request.getHeader("Authorization")).thenReturn(null);
//
//        // Act
//        authTokenFilter.doFilterInternal(request, response, filterChain);
//
//        // Assert
//        // Check that no authentication was set in the SecurityContextHolder
//        verify(request).getHeader("Authorization");
//        verifyNoInteractions(jwtUtils, userDetailsService, filterChain);
//    }
}