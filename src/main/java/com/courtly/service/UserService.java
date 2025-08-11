package com.courtly.service;

import com.courtly.dto.AuthRequest;
import com.courtly.dto.UserDto;
import com.courtly.entity.User;

public interface UserService extends CommonService<UserDto, User> {
    void save(AuthRequest authRequest);
}
