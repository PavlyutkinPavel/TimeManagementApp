package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "labels")
@Data
@Component
public class Label {
    @Id
    @SequenceGenerator(name = "labelsSeqGen", sequenceName = "labels_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "labelsSeqGen")
    private Long id;

    @NotNull
    @Column(name = "labels_category")
    private String category;

    @NotNull
    @Column(name = "labels_priority")
    private String priority;

    @NotNull
    @Column(name = "labels_style")
    private String style;

}