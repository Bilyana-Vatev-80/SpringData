package com.example.jsonexercise.repository;

import com.example.jsonexercise.modul.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    // Query 3
    @Query("SELECT c FROM Category c ORDER BY c.products.size")
   List<Category> findAllOrderByProductCount();
}
