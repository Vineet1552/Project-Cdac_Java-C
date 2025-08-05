package com.cdac.service;

import com.cdac.dao.ReviewDao;
import com.cdac.dao.UserDao;
import com.cdac.dao.WasherDao;
import com.cdac.dto.ReviewDto;
import com.cdac.entities.ReviewEntity;
import com.cdac.entities.UserEntity;
import com.cdac.entities.WasherEntity;
import com.cdac.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WasherDao washerDao;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public ReviewDto createReview(ReviewDto dto, String email) {
        UserEntity user = userDao.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        WasherEntity washer = washerDao.findById(dto.getWasherId())
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "id", dto.getWasherId()));

        ReviewEntity entity = new ReviewEntity();
        entity.setUser(user);
        entity.setWasher(washer);
        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());
        entity.setReviewDate(LocalDateTime.now());

        ReviewEntity saved = reviewDao.save(entity);
        return toDto(saved);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        ReviewEntity entity = reviewDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        return toDto(entity);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewDao.findAll()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {
        ReviewEntity entity = reviewDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        reviewDao.delete(entity);
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto dto) {
        ReviewEntity entity = reviewDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));

        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());

        if (dto.getReviewDate() != null) {
            entity.setReviewDate(LocalDateTime.parse(dto.getReviewDate(), formatter));
        }

        ReviewEntity updated = reviewDao.save(entity);
        return toDto(updated);
    }

    @Override
    public List<ReviewDto> getReviewsForWasherId(Long washerId) {
        WasherEntity washer = washerDao.findById(washerId)
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "id", washerId));

        return reviewDao.findAll().stream()
            .filter(r -> r.getWasher().getId().equals(washerId))
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    private ReviewDto toDto(ReviewEntity entity) {
        ReviewDto dto = new ReviewDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setWasherId(entity.getWasher().getId());
        dto.setRating(entity.getRating());
        dto.setComment(entity.getComment());
        dto.setReviewDate(entity.getReviewDate().format(formatter));
        return dto;
    }
}
