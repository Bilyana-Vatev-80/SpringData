package com.example.jsonexercise.modul.dto;

import com.example.jsonexercise.modul.dto.seedDto.ProductsCountAndPriceViewDto;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CategoryWithProductDto {
    @Expose
    private String name;
    @Expose
    private ProductsCountAndPriceViewDto productsInCategory;

    public CategoryWithProductDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductsCountAndPriceViewDto getProductsInCategory() {
        return productsInCategory;
    }

    public void setProductsInCategory(ProductsCountAndPriceViewDto productsInCategory) {
        this.productsInCategory = productsInCategory;
    }
}