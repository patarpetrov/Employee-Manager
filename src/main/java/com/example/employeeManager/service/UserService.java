package com.example.employeeManager.service;

import com.example.employeeManager.dto.UserDto;
import com.example.employeeManager.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
