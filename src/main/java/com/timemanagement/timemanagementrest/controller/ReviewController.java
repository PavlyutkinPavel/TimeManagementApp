package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.Review;
import com.timemanagement.timemanagementrest.domain.dto.ReviewDTO;
import com.timemanagement.timemanagementrest.exception.ReviewNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Review Controller", description = "makes all operations with reviews")
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final SecurityService securityService;

    public ReviewController(ReviewService reviewService, SecurityService securityService) {
        this.reviewService = reviewService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all reviews(for all admins)")
    @GetMapping
    public ResponseEntity<List<Review>> getReviews(Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            List<Review> reviews = reviewService.getReviews();
            if (reviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(reviews, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "get my reviews(for all review's author)")
    @GetMapping("/my_reviews")
    public ResponseEntity<List<Review>> getMyReviews(@RequestParam String myLogin, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || myLogin.equals(principal.getName().toString())){
            List<Review> reviews = reviewService.getMyReviews(myLogin);
            if (reviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(reviews, HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "get specific review(for all authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Long id, Principal principal) {
        Review review = reviewService.getReview(id).orElseThrow(ReviewNotFoundException::new);
        Long reviewUserId = review.getUserId();
        Long currentUserId = securityService.getUserIdByLogin(principal.getName());
        if (securityService.checkIfAdmin(principal.getName()) || reviewUserId == currentUserId){
            return new ResponseEntity<>(review, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "create review(for all authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createReview(@RequestBody ReviewDTO reviewDTO, Principal principal) {
        reviewService.createReview(reviewDTO, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "update reviews(for all admins and review's author)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateReview(@RequestBody Review review, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (review.getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            reviewService.updateReview(review);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete reviews(for all admins and review's author)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (getReview(id, principal).getBody().getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            reviewService.deleteReviewById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
