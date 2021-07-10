package com.example.dvancedqueringlab.repositories;

import com.example.dvancedqueringlab.entities.Shampoo;
import com.example.dvancedqueringlab.entities.Size;

import java.util.List;

public interface ShampooRepository extends BaseRepository<Shampoo> {
    List<Shampoo> findAllBySizeOrderById(Size size);
}
