package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    // Simulating a list of products
    private List<Product> products = new ArrayList<>();

    // Method to add a product
    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    // Method to fetch all products
    public List<Product> getAllProducts() {
        return products;
    }

    // Method to fetch product by ID
    public Product getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Method to update a product
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
        }
        return product;
    }

    // Method to delete a product
    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
}