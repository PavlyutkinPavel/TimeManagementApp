package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM reviews WHERE id = :reviewId")
    Optional<Review> findByIdReview(@Param("reviewId") Long reviewId);

    @Query(nativeQuery = true, value = "SELECT * FROM reviews")
    List<Review> findAllReviews();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO reviews(id, reviews_description, created_at, user_id) VALUES (:#{#review.id}, :#{#review.message}, :#{#review.created_at}, :#{#review.userId})")
    void saveReview(@Param("review") Review review);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE reviews SET reviews_description = :#{#review.message},  created_at = :#{#review.created_at}, user_id = :#{#review.userId} WHERE id = :#{#review.id}")
    void saveAndFlushReview(@Param("review") Review review);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM reviews WHERE id = :reviewId")
    void deleteByIdReview(@Param("reviewId") Long reviewId);

    @Query(value = "SELECT NEXTVAL('reviews_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
