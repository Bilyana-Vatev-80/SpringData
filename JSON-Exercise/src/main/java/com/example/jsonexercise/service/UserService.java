package com.example.jsonexercise.service;

import com.example.jsonexercise.modul.dto.UserSoldDto;
import com.example.jsonexercise.modul.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDto> findAllUserWithMoreThanOneSoldProducts();
}
