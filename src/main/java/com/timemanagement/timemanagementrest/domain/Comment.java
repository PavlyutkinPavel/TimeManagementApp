package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "comments")
@Data
@Component
public class Comment {
    @Id
    @SequenceGenerator(name = "commentsSeqGen", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "commentsSeqGen")
    private Long id;

    @NotNull
    @Column(name = "comments_content")
    private String title;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "post_id")
    private Long postId;

}