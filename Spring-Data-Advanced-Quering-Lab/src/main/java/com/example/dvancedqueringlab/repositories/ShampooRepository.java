package com.example.dvancedqueringlab.repositories;

import com.example.dvancedqueringlab.entities.Ingredient;
import com.example.dvancedqueringlab.entities.Label;
import com.example.dvancedqueringlab.entities.Shampoo;
import com.example.dvancedqueringlab.entities.Size;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooRepository extends BaseRepository<Shampoo> {
    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long label_id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE  i.name IN : name")
    List<Shampoo> findAllByIngredientsNames(List<String > names);

    @Query("SELECT s FROM Shampoo s WHERE s.ingredients.size < :ingredientCount")
    List<Shampoo> findAllByIngredientsCounts(int ingredientCount);

}
