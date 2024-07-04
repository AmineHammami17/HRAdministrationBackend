package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
