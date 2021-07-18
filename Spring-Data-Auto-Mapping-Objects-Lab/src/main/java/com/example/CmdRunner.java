package com.example;

import com.example.services.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;

public class CmdRunner implements CommandLineRunner {
    private final EmployeeService employeeService;

    public CmdRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
