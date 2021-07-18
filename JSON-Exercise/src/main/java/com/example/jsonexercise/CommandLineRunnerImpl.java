package com.example.jsonexercise;

import com.example.jsonexercise.modul.dto.CategoryWithProductDto;
import com.example.jsonexercise.modul.dto.ProductNameAndPriceDto;
import com.example.jsonexercise.modul.dto.UserSoldDto;
import com.example.jsonexercise.service.CategoryService;
import com.example.jsonexercise.service.ProductService;
import com.example.jsonexercise.service.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    public static final String OUTPUT_PATH = "C:\\Spring Data\\JSON-Exercise\\src\\main\\resources\\outPut/";
    public static final String PRODUCT_IN_RANGE_FILE_NAME = "product-in-range.json";
    public static final String USERS_AND_SOLD_PRODUCTS = "users-and-sold-products.json";
    public static final String CATEGORIES_AND_PRODUCTS = "categories-products.json";
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final Gson gson ;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
         seedDate();
         
        System.out.println("Please enter exercise:");
        int exNum = Integer.parseInt(bufferedReader.readLine());

        switch (exNum){
            case 1:
                productInRage();
            case 2:
                soldProduct();
            case 3:
                categoriesByProductsCount();
        }

    }

    private void categoriesByProductsCount() throws IOException {
        List<CategoryWithProductDto> categories = this.categoryService.getAllProductsCount();
        String content = gson.toJson(categories);
        writeToFile(OUTPUT_PATH + CATEGORIES_AND_PRODUCTS,content);
    }

    private void soldProduct() throws IOException {
        List<UserSoldDto> userSoldDtos = this.userService.findAllUserWithMoreThanOneSoldProducts();

        String content = gson.toJson(userSoldDtos);
        writeToFile(OUTPUT_PATH + USERS_AND_SOLD_PRODUCTS,content);
    }

    private void productInRage() throws IOException {
        List<ProductNameAndPriceDto> priceDtos = productService.
                findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));

        String content = gson.toJson(priceDtos);
        writeToFile(OUTPUT_PATH + PRODUCT_IN_RANGE_FILE_NAME,content);

    }

   private void writeToFile(String filePath, String content) throws IOException {
        Files.write(Path.of(filePath), Collections.singleton(content));
    }

    private void seedDate() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
