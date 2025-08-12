package com.courtly.service.impl;

import com.courtly.dao.UserDao;
import com.courtly.dto.AuthRequest;
import com.courtly.dto.UserDto;
import com.courtly.entity.User;
import com.courtly.enums.Role;
import com.courtly.mapper.UserMapper;
import com.courtly.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User, UserDao, UserDto, UserMapper> implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserDao dao, UserMapper mapper, PasswordEncoder encoder) {
        super(dao, mapper);
        this.userDao = dao;
        this.userMapper = mapper;
        this.encoder = encoder;
    }

    @Override
    public void save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void save(AuthRequest authRequest) {
        UserDto userDto = userMapper.toDto(authRequest);
        userDto.setRole(Role.USER);
        this.save(userDto);
    }
}
