package com.example.assignmentinternshipPetarPetrov.service.impl;

import com.example.assignmentinternshipPetarPetrov.dto.UserDto;
import com.example.assignmentinternshipPetarPetrov.entity.Role;
import com.example.assignmentinternshipPetarPetrov.entity.User;
import com.example.assignmentinternshipPetarPetrov.repository.RoleRepository;
import com.example.assignmentinternshipPetarPetrov.repository.UserRepository;
import com.example.assignmentinternshipPetarPetrov.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setEmployee(userDto.getEmployee());


        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        List<Role> role = roleRepository.findAll();
        if(role.size() == 0){
            Role role1 = new Role();
            role1.setName("ROLE_ADMIN");
            roleRepository.save(role1);
            user.setRoles(Arrays.asList(role1));
        }
        if (role.size() == 1){
            Role role1 = new Role();
            role1.setName("ROLE_USER");
            roleRepository.save(role1);
            user.setRoles(Arrays.asList(role1));
        }
        if (role.size() > 1){
            Role role1 = roleRepository.findByName("ROLE_USER");
            user.setRoles(Arrays.asList(role1));
        }
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

}
