package com.example.jsonexercise.service.impl;

import com.example.jsonexercise.constants.PathConstants;
import com.example.jsonexercise.modul.dto.CategoryWithProductDto;
import com.example.jsonexercise.modul.dto.seedDto.CategorySeedDto;
import com.example.jsonexercise.modul.dto.seedDto.ProductsCountAndPriceViewDto;
import com.example.jsonexercise.modul.entity.Category;
import com.example.jsonexercise.repository.CategoryRepository;
import com.example.jsonexercise.repository.ProductRepository;
import com.example.jsonexercise.service.CategoryService;
import com.example.jsonexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCategories() throws IOException {
        if(categoryRepository.count() > 0){
            return;
        }
        String fileContent = Files.readString(Path.of(PathConstants.RESOURCE_FILE_PATH_CATEGORIES));

        CategorySeedDto[] categorySeedDtos = gson.fromJson(fileContent, CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> findRandomCategories() {
        int categoryCount = ThreadLocalRandom.current().nextInt(1,3);
        Set<Category> categorySet = new HashSet<>();
        long totalCategories = categoryRepository.count();

        for (int i = 0; i < categoryCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, totalCategories + 1);

            categorySet.add(categoryRepository.findById(randomId).orElse(null));
        }
        return categorySet;
    }

    @Override
    public List<CategoryWithProductDto> getAllProductsCount() {
        return this.categoryRepository.findAllOrderByProductCount()
                .stream()
                .map(c -> {
                    CategoryWithProductDto categoriesDto = this.modelMapper.map(c, CategoryWithProductDto.class);
                    ProductsCountAndPriceViewDto productDto = new ProductsCountAndPriceViewDto();
                    productDto.setProductsCount(c.getProducts().size());
                    productDto.setAveragePrice(this.productRepository.getAveragePriceOnProductsInCategory(categoriesDto.getName()));
                    productDto.setTotalRevenue(this.productRepository.findTotalRevenue(categoriesDto.getName()));
                    categoriesDto.setProductsInCategory(productDto);

                    return categoriesDto;
                })
                .collect(Collectors.toList());
    }

}
