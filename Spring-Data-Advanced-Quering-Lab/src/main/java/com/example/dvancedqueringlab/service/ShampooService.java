package com.example.dvancedqueringlab.service;

import com.example.dvancedqueringlab.entities.Shampoo;
import com.example.dvancedqueringlab.entities.Size;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ShampooService {
    List<Shampoo> findAllBySizeOrderById(Size size);
}
