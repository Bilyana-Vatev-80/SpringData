package com.example.dvancedqueringlab.service;

import com.example.dvancedqueringlab.entities.Label;
import com.example.dvancedqueringlab.entities.Shampoo;
import com.example.dvancedqueringlab.entities.Size;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


public interface ShampooService {
    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelOrderByPrice(Size size, Long label_id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceLessThan(BigDecimal price);

    List<String> findAllByIngredientsNames(List<String > names);

    List<Shampoo> findAllByIngredientsCounts(int ingredientCount);
}
