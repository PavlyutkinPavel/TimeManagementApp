package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity(name = "tasks")
@Data
@Component
public class Task {
    @Id
    @SequenceGenerator(name = "tasksSeqGen", sequenceName = "tasks_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "tasksSeqGen")
    private Long id;

    @NotNull
    @Column(name = "tasks_title")
    private String title;

    @NotNull
    @Column(name = "tasks_description")
    private String description;

    @Column(name = "tasks_creation_date")
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "tasks_due_date")
    private LocalDateTime dueDate;//срок выполнения задания

    @Column(name = "time_for_task")
    private Long timeForTasks;//milliseconds from 1970

    @Column(name = "tasks_check")
    private Boolean check;

    @NotNull
    @Column(name = "progress")
    private Integer progress;//%

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "is_overdue")
    private Boolean isOverdue;

}