package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "products")
@Data
@Component
public class Product {
    @Id
    @SequenceGenerator(name = "productsSeqGen", sequenceName = "products_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "productsSeqGen")
    private Long id;

    @NotNull
    @Column(name = "products_title")
    private String title;

    @NotNull
    @Column(name = "products_description")
    private String description;

    @NotNull
    @Column(name = "products_price")
    private Long price;
}