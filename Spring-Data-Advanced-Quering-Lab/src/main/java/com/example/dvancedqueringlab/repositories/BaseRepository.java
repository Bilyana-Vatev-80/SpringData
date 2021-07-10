package com.example.dvancedqueringlab.repositories;

import com.example.dvancedqueringlab.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<EntityType extends BaseEntity> extends JpaRepository<EntityType, Long> {
}
