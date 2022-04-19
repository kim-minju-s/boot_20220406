package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ProductViewEntity;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductViewEntity, Long> {

}
