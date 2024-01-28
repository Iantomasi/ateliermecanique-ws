package com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach()
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testUserConstructor() {
        // Test the constructor
        User user = new User("John", "Doe", "123456789", "john.doe@example.com", "password123", new HashSet<>(), "profile.jpg");

        // Assert the values
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("123456789", user.getPhoneNumber());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("profile.jpg", user.getPicture());
        assertEquals(0, user.getRoles().size());
        assertNotNull(user.getUserIdentifier().getUserId());
        try {
            // Attempt to create a UUID from the string
            UUID.fromString(user.getUserIdentifier().getUserId());
        } catch (IllegalArgumentException e) {
            fail("userId is not a valid UUID");
        }
    }

    @Test
    void testRoleConstructor() {
        // Test the constructor
        Role role = new Role(ERole.ROLE_CUSTOMER);

        // Assert the values
        assertEquals(ERole.ROLE_CUSTOMER, role.getName());
    }



    @Test
    void loadUserByUsername_WhenUserExists_ShouldReturnUserDetails() {
        // Arrange
        String userEmail = "test@example.com";
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_CUSTOMER));
        User mockUser = new User(2, new UserIdentifier(), "John", "F", "3232", userEmail, null, "hha", roles);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(mockUser));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        // Assert
        assertEquals(UserDetailsImpl.class, userDetails.getClass());

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;

        // Check individual attributes
        assertEquals(mockUser.getId(), userDetailsImpl.getId());
        assertEquals(mockUser.getEmail(),userDetailsImpl.getUsername());
        assertEquals(mockUser.getUserIdentifier().getUserId(), userDetailsImpl.getUserId());
        assertEquals(mockUser.getFirstName(), userDetailsImpl.getFirstName());
        assertEquals(mockUser.getLastName(), userDetailsImpl.getLastName());
        assertEquals(mockUser.getPhoneNumber(), userDetailsImpl.getPhoneNumber());
        assertEquals(mockUser.getEmail(), userDetailsImpl.getEmail());
        assertEquals(mockUser.getPassword(), userDetailsImpl.getPassword());
        assertEquals(1, userDetailsImpl.getAuthorities().size());
        assertTrue(userDetailsImpl.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        assertTrue(userDetailsImpl.isAccountNonExpired());
        assertTrue(userDetailsImpl.isAccountNonLocked());
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
        assertTrue(userDetailsImpl.isEnabled());

        // Verify that userRepository.findByEmail was called with the correct argument
        verify(userRepository, times(1)).findByEmail(userEmail);
    }

    @Test
    void loadUserByUsername_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        String userEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Act and Assert
        // Ensure that UsernameNotFoundException is thrown when the user is not found
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(userEmail));

        // Verify that userRepository.findByEmail was called with the correct argument
        verify(userRepository, times(1)).findByEmail(userEmail);
    }

    @Test
    void testEquals_WhenSameInstance_ShouldReturnTrue() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl(1, "uuid", "John", "Doe", "123456789", "test@example.com", "password", new HashSet<>());

        // Act & Assert
        assertTrue(userDetails.equals(userDetails));
    }

    @Test
    void testEquals_WhenDifferentClass_ShouldReturnFalse() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl(1, "uuid", "John", "Doe", "123456789", "test@example.com", "password", new HashSet<>());

        // Act & Assert
        assertFalse(userDetails.equals("not a UserDetailsImpl"));
    }

    @Test
    void testEquals_WhenDifferentId_ShouldReturnFalse() {
        // Arrange
        UserDetailsImpl userDetails1 = new UserDetailsImpl(1, "uuid", "John", "Doe", "123456789", "test@example.com", "password", new HashSet<>());
        UserDetailsImpl userDetails2 = new UserDetailsImpl(2, "uuid", "John", "Doe", "123456789", "test@example.com", "password", new HashSet<>());

        // Act & Assert
        assertFalse(userDetails1.equals(userDetails2));
    }

    @Test
    void testEquals_WhenSameId_ShouldReturnTrue() {
        // Arrange
        UserDetailsImpl userDetails1 = new UserDetailsImpl(1, "uuid", "John", "Doe", "123456789", "test@example.com", "password", new HashSet<>());
        UserDetailsImpl userDetails2 = new UserDetailsImpl(1, "uuid", "Jane", "Doe", "987654321", "jane@example.com", "secret", new HashSet<>());

        // Act & Assert
        assertTrue(userDetails1.equals(userDetails2));
    }

}