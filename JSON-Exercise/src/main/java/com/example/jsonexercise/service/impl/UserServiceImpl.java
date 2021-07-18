package com.example.jsonexercise.service.impl;

import com.example.jsonexercise.constants.PathConstants;
import com.example.jsonexercise.modul.dto.ProductsNameAndPriceBuyerFirstAndLastNameDto;
import com.example.jsonexercise.modul.dto.seedDto.UserSeedDto;
import com.example.jsonexercise.modul.dto.UserSoldDto;
import com.example.jsonexercise.modul.entity.User;
import com.example.jsonexercise.repository.UserRepository;
import com.example.jsonexercise.service.UserService;
import com.example.jsonexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws IOException {
        if(userRepository.count() == 0){
         Arrays.stream(gson.fromJson(
                 Files.readString(Path.of(PathConstants.RESOURCE_FILE_PATH_USERS)),UserSeedDto[].class))
                 .filter(validationUtil::isValid)
                 .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                 .forEach(userRepository::save);

        }

//        String fileContent = Files.readString(Path.of(PathConstants.RESOURCE_FILE_PATH_USERS));
//
//        UserSeedDto[] userSeedDtos = gson.fromJson(fileContent, UserSeedDto[].class);
//
//        Arrays.stream(userSeedDtos).filter(validationUtil::isValid)
//                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
//                .forEach(userRepository::save);

    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1,userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUserWithMoreThanOneSoldProducts() {
       return this.userRepository.findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName()
               .stream()
               .map(user -> {
                   UserSoldDto userSoldDtos = this.modelMapper.map(user, UserSoldDto.class);

                   userSoldDtos.setSoldProduct(
                           user.getSellerProducts()
                           .stream().filter(s -> s.getBuyer() != null)
                           .map(order -> this.modelMapper.map(order, ProductsNameAndPriceBuyerFirstAndLastNameDto.class))
                           .collect(Collectors.toSet()));

                           return userSoldDtos;
               })
               .collect(Collectors.toList());

    }
}
