package com.courtly.controller;

import com.courtly.dto.AuthRequest;
import com.courtly.service.UserService;
import com.courtly.service.impl.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_ValidCredentialsReturnsJwtToken() {
        String username = "john";
        String password = "secret";
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);
        when(jwtService.generateToken(username)).thenReturn("mocked-jwt-token");

        ResponseEntity<?> response = authController.login(basicAuth);

        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        assertTrue(response.getHeaders().containsKey(HttpHeaders.AUTHORIZATION));
        assertEquals("Bearer mocked-jwt-token", response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(username);
    }

    @Test
    void login_InvalidCredentialsThrowsException() {
        String username = "john";
        String password = "wrong";
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(false);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);

        assertThrows(IllegalArgumentException.class, () -> authController.login(basicAuth));
    }

    @Test
    void register() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setUsername("john");
        request.setPassword("pass");

        // Act
        ResponseEntity<?> response = authController.register(request);

        // Assert
        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        verify(userService).save(request);
    }
}
