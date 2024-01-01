package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity(name = "wallets")
@Component
public class Wallet {

    @Id
    @SequenceGenerator(name = "walletsSeqGen", sequenceName = "wallets_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "walletsSeqGen")
    private Long id;

    @NotNull
    @Column(name = "balance")
    private Long balance;

    @NotNull
    @Column(name = "user_id")
    private Long userId;
}
