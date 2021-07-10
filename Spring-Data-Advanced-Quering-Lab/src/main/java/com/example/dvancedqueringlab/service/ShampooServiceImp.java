package com.example.dvancedqueringlab.service;

import com.example.dvancedqueringlab.entities.Shampoo;
import com.example.dvancedqueringlab.entities.Size;
import com.example.dvancedqueringlab.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShampooServiceImp implements ShampooService{

    private final ShampooRepository shampooRepository;

    public ShampooServiceImp(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findAllBySizeOrderById(Size size) {
        return this.shampooRepository.findAllBySizeOrderById(size);
    }
}
