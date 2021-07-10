package com.example.dvancedqueringlab;

import com.example.dvancedqueringlab.service.ShampooService;
import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;

public class Main implements CommandLineRunner {
    private final ShampooService shampooService;

    public Main(ShampooService shampooService) {
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("TASK 1");

    }
}
