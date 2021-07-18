package com.example.service.impl;

import com.example.model.dto.*;
import com.example.model.entity.Game;
import com.example.repository.GameRepository;
import com.example.service.GameService;
import com.example.service.UserService;
import com.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    public final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private  UserDto userDto;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void addGame(GameAddDto gameAddDto) {
        if(!this.userService.isLoggedUserAdmin()){
            System.out.println("Logged user is not ADMIN and can't add game!");
            return;
        }

        Game game = this.modelMapper.map(gameAddDto, Game.class);
        this.gameRepository.save(game);
    }

    @Override
    public void editGame(GameEditDto gameEditDto) {
        if(!this.userService.isLoggedUserAdmin()){
            System.out.println("Logged user is not ADMIN and can't edit games!");
            return;
        }

        Game game = this.modelMapper.map(gameEditDto, Game.class);
        this.gameRepository.save(game);
        System.out.println("Edit" + game.getTitle());
    }

    @Override
    public GameEditDto findOneById(long id) {
        Optional<Game> game = this.gameRepository.findById(id);
        return game.map(g -> this.modelMapper.map(g, GameEditDto.class)).orElse(null);
    }

    @Override
    public GameDeleteDto findById(long id) {
        Optional<Game> game = this.gameRepository.findById(id);
        return game.map(g -> this.modelMapper.map(g, GameDeleteDto.class)).orElse(null);
    }

    @Override
    public void deleteGame(GameDeleteDto gameDeleteDto) {
        Game game = this.modelMapper.map(gameDeleteDto, Game.class);
        this.gameRepository.deleteById(gameDeleteDto.getId());
    }

    @Override
    public Set<GameTitleAndPriceViewDto> getAllGameTitlesAndPrices() {
        return this.gameRepository
                .findAll()
                .stream()
                .map(game -> this.modelMapper.map(game, GameTitleAndPriceViewDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public GameSingleTitleDetailsViewDto getSingleTitleDetails(String title) {
        if (!this.gameRepository.existsByTitleEquals(title)) {
            System.out.println("Game with the given title doesn't exist in database!!!");
        }

        return this.modelMapper
                .map(this.gameRepository.getGameByTitleEquals(title),
                        GameSingleTitleDetailsViewDto.class);
    }

}
