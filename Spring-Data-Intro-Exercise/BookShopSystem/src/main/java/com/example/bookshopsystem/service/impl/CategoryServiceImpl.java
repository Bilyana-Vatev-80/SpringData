package com.example.bookshopsystem.service.impl;

import com.example.bookshopsystem.model.entities.Category;
import com.example.bookshopsystem.repository.CategoryRepository;
import com.example.bookshopsystem.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    // при мен краткия path не върши работа
    private static final String CATEGORIES_FILE_PATH = "C:\\Spring Data\\Spring-Data-Intro-Exercise\\BookShopSystem\\src\\main\\resources\\files\\categories.txt";

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if(this.categoryRepository.count() > 0){
            return;
        }
        Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(row -> !row.isEmpty())
                .forEach(categoryName -> {
                    Category category = new Category(categoryName);

                    categoryRepository.save(category);
                });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int randInt = ThreadLocalRandom.current().nextInt(1, 3);

        long catRepoCount = categoryRepository.count();
        for (int i = 0; i < randInt; i++) {
            long randId = ThreadLocalRandom.current().nextLong(1,catRepoCount + 1);

            Category category = categoryRepository.findById(randId).orElse(null);
            categories.add(category);
        }
        return categories;
    }


}
