package com.example.dvancedqueringlab.entities;

import javax.persistence.*;
import java.util.Arrays;

@MappedSuperclass
public abstract class BaseEntity {

    private Long id;

    protected BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @Override
//    public String toString() {
//        return Arrays.stream(Arrays.stream(this.getClass().getDeclaredFields()).map())
//    }
}
