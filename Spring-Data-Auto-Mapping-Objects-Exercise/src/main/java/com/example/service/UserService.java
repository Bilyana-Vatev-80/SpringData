package com.example.service;

import com.example.model.dto.UserLoginDto;
import com.example.model.dto.UserRegisterDto;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logOut();

    boolean isLoggedUserAdmin();
}
