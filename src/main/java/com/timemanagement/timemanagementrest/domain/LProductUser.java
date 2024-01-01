package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "l_products_users")
@Data
@Component
public class LProductUser {
    @Id
    @SequenceGenerator(name = "l_products_usersSeqGen", sequenceName = "l_products_users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "l_products_usersSeqGen")
    private Long id;

    @Column(name = "products_id")
    private Long productsId;

    @Column(name = "users_id")
    private Long usersId;

}