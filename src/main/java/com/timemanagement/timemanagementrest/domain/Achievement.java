package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "achievements")
@Data
@Component
public class Achievement {
    @Id
    @SequenceGenerator(name = "achievementsSeqGen", sequenceName = "achievements_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "achievementsSeqGen")
    private Long id;

    @NotNull
    @Column(name = "achievements_name")
    private String name;

    @NotNull
    @Column(name = "achievements_description")
    private String description;

    @NotNull
    @Column(name = "achievements_styles")
    private String styles;

}