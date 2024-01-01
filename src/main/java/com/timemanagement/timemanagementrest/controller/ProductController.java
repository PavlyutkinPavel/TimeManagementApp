package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.Product;
import com.timemanagement.timemanagementrest.exception.ProductNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Product Controller", description = "makes all operations with products")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final SecurityService securityService;

    public ProductController(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all products(for all authorized users)")
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @Operation(summary = "get specific product(for all authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id).orElseThrow(ProductNotFoundException::new);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(summary = "create product(for all authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@RequestParam String products_title, @RequestParam String products_description, @RequestParam Long products_price, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            productService.createProduct(products_title, products_description, products_price);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "update products(for all admins and product's author)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateProduct(@Valid @RequestBody Product product, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            productService.updateProduct(product);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete products(for all admins and product's author)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            productService.deleteProductById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
