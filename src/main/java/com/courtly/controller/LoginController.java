package com.courtly.controller;

import com.courtly.dto.AuthRequest;
import com.courtly.service.UserService;
import com.courtly.service.impl.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String basicAuth) {
        String base64Credentials = basicAuth.substring("Basic ".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] values = credentials.split(":", 2);

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(values[0],
                                                                                                  values[1]));

        if (authentication.isAuthenticated()) {
            String jwt = jwtService.generateToken(values[0]);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid user request!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody @Valid AuthRequest user) {
        userService.save(user);
        return ResponseEntity.ok().build();
    }
}
