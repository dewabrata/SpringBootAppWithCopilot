package com.erdjbe26.webservice.service;

import com.erdjbe26.webservice.dto.RegisterUserDto;
import com.erdjbe26.webservice.dto.UserDto;

public interface UserService {
    UserDto registerUser(RegisterUserDto registerUserDto);
}
