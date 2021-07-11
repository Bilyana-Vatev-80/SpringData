package com.example.dvancedqueringlab.service;

import com.example.dvancedqueringlab.entities.Label;
import com.example.dvancedqueringlab.entities.Shampoo;
import com.example.dvancedqueringlab.entities.Size;
import com.example.dvancedqueringlab.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImp implements ShampooService{

    private final ShampooRepository shampooRepository;

    public ShampooServiceImp(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findAllBySizeOrderById(Size size) {
        return this.shampooRepository.findAllBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> findAllBySizeOrLabelOrderByPrice(Size size, Long label_id) {
        return shampooRepository.findAllBySizeOrLabelIdOrderByPrice(size,label_id);
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countAllByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public List<String> findAllByIngredientsNames(List<String> names) {
        return this.shampooRepository.findAllByIngredientsNames(names).
                stream().map(Shampoo::getBrand)
                .collect(Collectors.toList());
    }

    @Override
    public List<Shampoo> findAllByIngredientsCounts(int ingredientCount) {
        return this.shampooRepository.findAllByIngredientsCounts(ingredientCount);
    }

}
