package com.example.service.impl;

import com.example.model.dto.UserDto;
import com.example.model.dto.UserLoginDto;
import com.example.model.dto.UserRegisterDto;
import com.example.model.entity.Role;
import com.example.model.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private UserDto loggedUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {

        User user = this.modelMapper.map(userRegisterDto, User.class);

        user.setRole(this.userRepository.count()== 0 ? Role.ADMIN : Role.USER);

        this.userRepository.save(user);
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        User user = this.userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword()).orElse(null);

        if(user == null){
            System.out.println("Incorrect username / password");
        } else {
            this.loggedUser = this.modelMapper.map(user, UserDto.class);
            System.out.printf("Successfully logged in %s%n",user.getFullName());
        }
    }

    @Override
    public void logOut() {
        if(this.loggedUser == null){
            System.out.printf("Cannot log out. No user was logged in.%n");
        } else {
            System.out.printf("User %s successfully logged out%n",this.loggedUser.getPassword());
            this.loggedUser = null;
        }
    }

    @Override
    public boolean isLoggedUserAdmin() {
        return this.loggedUser.getRole().equals(Role.ADMIN);
    }
}
