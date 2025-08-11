package com.courtly.controller;

import com.courtly.dto.UserDto;
import com.courtly.entity.User;
import com.courtly.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class UserController extends CommonController<User, UserDto, UserService>{
    public UserController(UserService service) {
        super(service);
    }
}
