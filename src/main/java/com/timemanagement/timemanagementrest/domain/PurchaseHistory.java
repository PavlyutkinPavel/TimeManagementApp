package com.timemanagement.timemanagementrest.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Schema(description = "История покупок")
@Data
@Entity(name = "purchase_history")
public class PurchaseHistory {

    @Schema(description = "Это уникальный идентификатор пользователя")
    @Id
    @SequenceGenerator(name = "purchase_historySeqGen", sequenceName = "purchase_history_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "purchase_historySeqGen")
    private Long id;

    @NotNull
    @Column(name = "product_id")
    private Long productId;

    @NotNull
    @Column(name = "total_amount")
    private Long totalAmount;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

}