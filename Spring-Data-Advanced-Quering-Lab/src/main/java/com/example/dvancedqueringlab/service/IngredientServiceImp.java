package com.example.dvancedqueringlab.service;

import com.example.dvancedqueringlab.entities.Ingredient;
import com.example.dvancedqueringlab.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class IngredientServiceImp implements IngredientService{

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImp(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAllByNameStartingWith(String name) {
        return ingredientRepository.findAllByNameStartingWith(name);
    }

    @Override
    public List<Ingredient> findAllByNameIn(Collection<String> name) {
        return this.findAllByNameIn(name);
    }

    @Override
    @Transactional
    public void deleteAllByName(String name) {
        this.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void updatePrice() {
        this.updatePrice();
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public int updatePrice(double priceChange, Collection<String> names) {
        return this.updatePrice(priceChange, names);
    }
}
