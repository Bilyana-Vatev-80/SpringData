package com.example.dvancedqueringlab.repositories;

import com.example.dvancedqueringlab.entities.Ingredient;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {

    List<Ingredient> findAllByNameStartingWith(String name);

    List<Ingredient> findAllByNameIn(Collection<String> name);

    @Query("DELETE FROM Ingredient i WHERE i.name = :name")
    @Modifying
    void deleteAllByName(String name);

    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1")
    @Modifying
    void updatePrice();

    @Query("UPDATE Ingredient SET price = price * : priceChange WHERE name IN : names")
    int updatePrice(double priceChange, Collection<String> names );
}
