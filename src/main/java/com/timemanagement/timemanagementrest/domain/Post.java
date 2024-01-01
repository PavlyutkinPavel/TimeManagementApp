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

@Entity(name = "posts")
@Data
@Component
public class Post {
    @Id
    @SequenceGenerator(name = "postSeqGen", sequenceName = "posts_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "postSeqGen")
    private Long id;

    @NotNull
    @Column(name = "post_text")
    private String title;

    @NotNull
    @Column(name = "description")
    private String content;

    @NotNull
    @Column(name = "post_likes")
    private Long likes;

    @NotNull
    @Column(name = "post_dislikes")
    private Long dislikes;

    @NotNull
    @Column(name = "comment_number")
    private Long comments;

    @NotNull
    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

}