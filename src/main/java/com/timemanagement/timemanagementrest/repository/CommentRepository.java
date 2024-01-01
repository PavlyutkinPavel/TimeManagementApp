package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM comments WHERE id = :commentId")
    Optional<Comment> findByIdComment(@Param("commentId") Long commentId);

    @Query(nativeQuery = true, value = "SELECT * FROM comments")
    List<Comment> findAllComments();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO comments(id, comments_content, user_id, post_id) VALUES (:#{#comment.id}, :#{#comment.title}, :#{#comment.userId}, :#{#comment.postId})")
    void saveComment(@Param("comment") Comment comment);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE comments SET comments_content = :#{#comment.title},  user_id = :#{#comment.userId}, post_id = :#{#comment.postId} WHERE id = :#{#comment.id}")
    void saveAndFlushComment(@Param("comment") Comment comment);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM comments WHERE id = :commentId")
    void deleteByIdComment(@Param("commentId") Long commentId);

    @Query(value = "SELECT NEXTVAL('comments_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
