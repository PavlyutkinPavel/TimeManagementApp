package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Label;
import com.timemanagement.timemanagementrest.domain.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM posts WHERE id = :postId ")
    Optional<Post> findByIdPost(Long postId);

    @Query(nativeQuery = true, value = "SELECT * FROM posts")
    List<Post> findAllPosts();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO posts(id, post_text, description, post_likes, post_dislikes, comment_number, created_date, user_id) VALUES (:#{#post.id}, :#{#post.title}, :#{#post.content}, :#{#post.likes}, :#{#post.dislikes}, :#{#post.comments}, :#{#post.createdAt}, :#{#post.userId})")
    void savePost(@Param("post") Post post);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE posts SET post_text = :#{#post.title}, description = :#{#post.content}, post_likes = :#{#post.likes}, post_dislikes = :#{#post.dislikes}, comment_number = :#{#post.comments}, created_date = :#{#post.createdAt}, user_id = :#{#post.userId} WHERE id = :#{#post.id}")
    void saveAndFlushPost(@Param("post") Post post);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM posts WHERE id = :id")
    void deleteByIdPost(@Param("id") Long id);

    @Query(value = "SELECT NEXTVAL('posts_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
