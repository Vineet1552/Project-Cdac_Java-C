package com.cdac.service;

import java.util.List;

import com.cdac.dto.ReviewDto;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviews();
    void deleteReview(Long id);
    ReviewDto updateReview(Long id, ReviewDto reviewDto);
}
