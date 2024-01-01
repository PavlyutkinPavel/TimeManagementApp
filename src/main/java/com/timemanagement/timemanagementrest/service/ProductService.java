package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Product;
import com.timemanagement.timemanagementrest.repository.ProductRepository;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final Product product;

    @Autowired
    public ProductService(ProductRepository productRepository, Product product) {
        this.productRepository = productRepository;
        this.product = product;
    }

    public List<Product> getProducts() {
        return productRepository.findAllProducts();
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findByIdProduct(id);
    }

    public void createProduct(String title, String description, Long price) {
        product.setId(productRepository.getNextSequenceValue());
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        productRepository.saveProduct(product);
    }

    public void updateProduct(Product product) {
        productRepository.saveAndFlushProduct(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteByIdProduct(id);
    }
}