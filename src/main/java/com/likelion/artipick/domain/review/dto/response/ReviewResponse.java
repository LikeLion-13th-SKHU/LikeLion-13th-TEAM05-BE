package com.likelion.artipick.domain.review.dto.response;

import com.likelion.artipick.domain.review.Review;

public record ReviewResponse(
        Long id,
        String content,
        int rating,
        int viewCount,
        int likeCount,
        Long userId,
        String createdAt,
        String updatedAt
) {
    public static ReviewResponse from(Review r) {
        return new ReviewResponse(
                r.getId(),
                r.getContent(),
                r.getRating(),
                r.getViewCount(),
                r.getLikeCount(),
                r.getUserId(),
                r.getCreatedAt() != null ? r.getCreatedAt().toString() : null,
                r.getUpdatedAt() != null ? r.getUpdatedAt().toString() : null
        );
    }
}
