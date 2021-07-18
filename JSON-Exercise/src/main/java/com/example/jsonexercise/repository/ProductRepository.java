package com.example.jsonexercise.repository;

import com.example.jsonexercise.modul.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    //Query 1
    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal price, BigDecimal price2);

    //Query 3 - averagePrice
    @Query("select avg (p.price), c from Product p " +
            "join p.categories c " +
            "where c.name = :categoryName " +
            "group by c.id")
    BigDecimal getAveragePriceOnProductsInCategory(String categoryName);

    //Query 3 - totalPrice
    @Query("select sum (p.price) from Product p " +
            "join p.categories c " +
            "where c.name = :categoryName " +
            "group by c.id")
    BigDecimal findTotalRevenue(String categoryName);
}
