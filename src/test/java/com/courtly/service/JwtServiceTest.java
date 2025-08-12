package com.courtly.service;

import com.courtly.service.impl.JwtService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private static final String TEST_SECRET_BASE64;
    private static final long TEST_EXPIRATION_MS = 60000L; // 1 min

    static {
        // generate a valid HS256 key and base64-encode it
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        TEST_SECRET_BASE64 = Base64.getEncoder().encodeToString(key.getEncoded());
    }

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        setPrivateField(jwtService, "jwtSecret", TEST_SECRET_BASE64);
        setPrivateField(jwtService, "expiration", TEST_EXPIRATION_MS);
    }

    private static void setPrivateField(Object target, String fieldName, Object value) {
        try {
            Field field = JwtService.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().equals(long.class) && value instanceof Number) {
                field.setLong(target, ((Number) value).longValue());
            } else {
                field.set(target, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to set private field " + fieldName, e);
        }
    }

    @Test
    void generateToken_and_extractUsername() {
        String username = "testusername";
        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isBlank());
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    void extractExpiration_isInFuture() {
        String token = jwtService.generateToken("testusername");
        assertTrue(jwtService.extractExpiration(token).after(new java.util.Date()));
    }

    @Test
    void validateToken_ValidForSameUser() {
        String username = "testusername";
        UserDetails ud = new User(username, "pw", List.of());
        String token = jwtService.generateToken(username);
        assertTrue(jwtService.validateToken(token, ud));
    }

    @Test
    void validateToken_InvalidForDifferentUser() {
        String token = jwtService.generateToken("testusername");
        UserDetails ud = new User("otherusername", "pw", List.of());
        assertFalse(jwtService.validateToken(token, ud));
    }

//    @Test
//    void validateToken_FalseWhenExpired() {
//        // shorten expiration for this test
//        setPrivateField(jwtService, "expiration", 658L); // 0.5s
//        String username = "username";
//        UserDetails ud = new User(username, "pw", List.of());
//        String token = jwtService.generateToken(username);
//
////        Thread.sleep(1000L); // wait until expired
//        assertFalse(jwtService.validateToken(token, ud));
//    }
}
