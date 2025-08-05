package com.cdac.service;

import java.util.List;

import com.cdac.dto.ReviewDto;
import com.cdac.entities.UserEntity;

public interface ReviewService {
    ReviewDto createReview(ReviewDto dto, String email); 
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviews();
    void deleteReview(Long id);
    ReviewDto updateReview(Long id, ReviewDto reviewDto);
    List<ReviewDto> getReviewsForWasherId(Long washerId);

}
