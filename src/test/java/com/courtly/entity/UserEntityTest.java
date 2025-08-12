package com.courtly.entity;

import com.courtly.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class UserEntityTest {
    @Test
    void getAuthorities_ReturnsCorrectRole() {
        // Arrange
        User user = new User();
        user.setRole(Role.ADMIN); // Assuming Role is an enum with ADMIN, USER, etc.

        // Act
        List<GrantedAuthority> authorities = user.getAuthorities();

        // Assert
        Assertions.assertNotNull(authorities);
        Assertions.assertEquals(1, authorities.size());
        Assertions.assertEquals(new SimpleGrantedAuthority("ROLE_ADMIN"), authorities.getFirst());
    }

    @Test
    void toUserDetails_ReturnsCorrectUserDetails() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("secret");
        user.setRole(Role.USER);

        UserDetails userDetails = user.toUserDetails();

        // Assert
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals("john", userDetails.getUsername());
        Assertions.assertEquals("secret", userDetails.getPassword());
        Assertions.assertEquals(1, userDetails.getAuthorities().size());
        Assertions.assertTrue(userDetails.getAuthorities()
                           .contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
