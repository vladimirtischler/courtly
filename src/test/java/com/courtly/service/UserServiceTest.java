package com.courtly.service;

import com.courtly.dao.UserDao;
import com.courtly.dto.AuthRequest;
import com.courtly.dto.UserDto;
import com.courtly.entity.User;
import com.courtly.enums.Role;
import com.courtly.mapper.UserMapper;
import com.courtly.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("rawpass");
        userDto.setRole(Role.ADMIN);

        user = new User();
        user.setUsername("testuser");
        user.setPassword("rawpass");
        user.setRole(Role.ADMIN);
    }

    @Test
    void save_withUserDto() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(encoder.encode("rawpass")).thenReturn("encodedPass");

        userService.save(userDto);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDao).save(captor.capture());
        User savedUser = captor.getValue();

        Assertions.assertEquals("encodedPass", savedUser.getPassword());
        Assertions.assertEquals("testuser", savedUser.getUsername());

        verify(userMapper).toEntity(userDto);
        verify(encoder).encode("rawpass");
    }

    @Test
    void save_withAuthRequest() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("john");
        authRequest.setPassword("123");

        UserDto mappedDto = new UserDto();
        mappedDto.setUsername("john");
        mappedDto.setPassword("123");

        when(userMapper.toDto(authRequest)).thenReturn(mappedDto);
        when(userMapper.toEntity(mappedDto)).thenReturn(user);
        when(encoder.encode(anyString())).thenReturn("encoded123");

        userService.save(authRequest);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDao).save(captor.capture());
        User savedUser = captor.getValue();

        Assertions.assertEquals("encoded123", savedUser.getPassword());
        Assertions.assertEquals("testuser", savedUser.getUsername());
        Assertions.assertEquals(Role.USER, mappedDto.getRole());

        verify(userMapper).toDto(authRequest);
        verify(userMapper).toEntity(mappedDto);
        verify(encoder).encode(anyString());
    }
}
