package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Review;
import com.timemanagement.timemanagementrest.domain.dto.ReviewDTO;
import com.timemanagement.timemanagementrest.repository.ReviewRepository;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final SecurityService securityService;
    private final Review review;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, SecurityService securityService, Review review) {
        this.reviewRepository = reviewRepository;
        this.securityService = securityService;
        this.review = review;
    }

    public List<Review> getReviews() {
        return reviewRepository.findAllReviews();
    }

    public List<Review> getMyReviews(String myLogin) {
        List<Review> reviewList = reviewRepository.findAllReviews();
        List<Review> newReviewList = new ArrayList<>(){};
        for (Review review : reviewList) {
            if(review.getUserId() == securityService.getUserIdByLogin(myLogin)){
                newReviewList.add(review);
            }
        }
        return newReviewList;
    }

    public Optional<Review> getReview(Long id) {
        return reviewRepository.findByIdReview(id);
    }

    public void createReview(ReviewDTO reviewDTO, Principal principal) {
        review.setId(reviewRepository.getNextSequenceValue());
        review.setMessage(reviewDTO.getMessage());
        review.setCreated_at(LocalDateTime.now());
        review.setUserId(securityService.getUserIdByLogin(principal.getName()));
        reviewRepository.saveReview(review);
    }

    public void updateReview(Review review) {
        reviewRepository.saveAndFlushReview(review);
    }

    public void deleteReviewById(Long id) {
        reviewRepository.deleteByIdReview(id);
    }
}