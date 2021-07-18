package com.example.service;

import com.example.model.dto.*;

import java.util.Set;

public interface GameService {
    void addGame(GameAddDto gameAddDto);
    void editGame(GameEditDto gameEditDto);
    GameEditDto findOneById(long id);
    GameDeleteDto findById(long id);
    void deleteGame(GameDeleteDto gameDeleteDto);
    Set<GameTitleAndPriceViewDto> getAllGameTitlesAndPrices();
    GameSingleTitleDetailsViewDto getSingleTitleDetails(String title);

}
