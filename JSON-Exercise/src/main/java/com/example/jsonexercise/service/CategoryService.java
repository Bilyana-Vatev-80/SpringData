package com.example.jsonexercise.service;

import com.example.jsonexercise.modul.dto.CategoryWithProductDto;
import com.example.jsonexercise.modul.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;
    Set<Category> findRandomCategories();
    List<CategoryWithProductDto> getAllProductsCount();

}
