package com.example.dvancedqueringlab;

import com.example.dvancedqueringlab.entities.Size;
import com.example.dvancedqueringlab.service.IngredientService;
import com.example.dvancedqueringlab.service.ShampooService;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main implements CommandLineRunner {
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public Main(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("TASK 1");
        System.out.println("Enter size (SMALL, MEDIUM, LARGE): ");
        Size size = Size.valueOf(sc.nextLine());

        this.shampooService.findAllBySizeOrderById(size)
                .forEach(System.out::println);

        System.out.println();
        System.out.println("TASK 2");
        System.out.println("Enter size (SMALL, MEDIUM, LARGE): ");
        size = Size.valueOf(sc.nextLine());
        long label = Long.parseLong(sc.nextLine());

        this.shampooService.findAllBySizeOrLabelOrderByPrice(size,label)
        .forEach(System.out::println);

        System.out.println();
        System.out.println("TASK 3");

        System.out.println("Enter price: ");
        BigDecimal price = new BigDecimal(sc.nextLine());

        this.shampooService.findAllByPriceGreaterThanOrderByPriceDesc(price).forEach(System.out::println);

        System.out.println();
        System.out.println("TASK 4");

        System.out.println("Enter name: ");
        String nameStartsWith = sc.nextLine();

        this.ingredientService.findAllByNameStartingWith(nameStartsWith)
                .forEach(System.out::println);


        System.out.println();
        System.out.println("TASK 5");

        System.out.println("Enter names: ");
        List<String> names = Arrays.asList(sc.nextLine().split(" "));

        this.ingredientService.findAllByNameIn(names).forEach(System.out::println);


        System.out.println();
        System.out.println("TASK 6");

        System.out.println("Enter price: ");
        price = new BigDecimal(sc.nextLine());

        System.out.println(this.shampooService.countAllByPriceLessThan(price));


        System.out.println();
        System.out.println("TASK 7");

        System.out.println("Enter ingredient names: ");
        List<String> ingredientNames = Arrays.asList(sc.nextLine().split(" "));

        this.shampooService.findAllByIngredientsNames(ingredientNames).forEach(System.out::println);


        System.out.println();
        System.out.println("TASK 8");

        System.out.println("Enter number: ");

        int count = Integer.parseInt(sc.nextLine());

        this.shampooService.findAllByIngredientsCounts(count).forEach(System.out::println);


        System.out.println();
        System.out.println("TASK 9");
        String nameForDelete = sc.nextLine();

        this.ingredientService.deleteAllByName(nameForDelete);


        System.out.println();
        System.out.println("Enter 10");

        this.ingredientService.findAll().forEach(System.out::println);
        this.ingredientService.updatePrice();


        System.out.println();
        System.out.println("TASK 11");
        System.out.println("Enter price: ");

        double priceFactor = Double.parseDouble(sc.nextLine());
        System.out.println("Enter name: ");
        List<String> nameForUpdate = Arrays.asList(sc.nextLine().split(" "));

        System.out.println("CHANGED: " + this.ingredientService.updatePrice(priceFactor, nameForUpdate));






    }

}
