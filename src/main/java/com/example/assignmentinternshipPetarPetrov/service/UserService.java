package com.example.assignmentinternshipPetarPetrov.service;

import com.example.assignmentinternshipPetarPetrov.dto.UserDto;
import com.example.assignmentinternshipPetarPetrov.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
