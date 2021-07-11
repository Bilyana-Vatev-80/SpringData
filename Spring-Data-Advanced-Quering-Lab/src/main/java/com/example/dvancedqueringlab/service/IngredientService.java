package com.example.dvancedqueringlab.service;

import com.example.dvancedqueringlab.entities.Ingredient;

import java.util.Collection;
import java.util.List;

public interface IngredientService {
    List<Ingredient> findAllByNameStartingWith(String name);

    List<Ingredient> findAllByNameIn(Collection<String> name);

    void deleteAllByName(String name);

    void updatePrice();

    List<Ingredient> findAll();

    int updatePrice(double priceChange, Collection<String> names );
}
