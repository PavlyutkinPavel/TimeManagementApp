package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE id = :productId")
    Optional<Product> findByIdProduct(@Param("productId") Long productId);

    @Query(nativeQuery = true, value = "SELECT * FROM products")
    List<Product> findAllProducts();

    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE products_title = :productTitle")
    Optional<Product> findByTitleProduct(@Param("productTitle") String productTitle);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO products(id, products_title, products_description, products_price) VALUES (:#{#product.id}, :#{#product.title}, :#{#product.description}, :#{#product.price})")
    void saveProduct(@Param("product") Product product);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE products SET products_title = :#{#product.title},  products_description = :#{#product.description}, products_price = :#{#product.price} WHERE id = :#{#product.id}")
    void saveAndFlushProduct(@Param("product") Product product);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM products WHERE id = :productId")
    void deleteByIdProduct(@Param("productId") Long productId);

    @Query(value = "SELECT NEXTVAL('achievements_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
