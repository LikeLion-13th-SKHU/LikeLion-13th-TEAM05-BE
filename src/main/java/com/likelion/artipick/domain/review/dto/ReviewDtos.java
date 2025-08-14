package com.likelion.artipick.domain.review.dto;

import com.likelion.artipick.domain.review.Review;
import lombok.*;

public class ReviewDtos {

    /* 리뷰 생성 요청 DTO */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        private String content;
        private int rating;
        private Long userId;

        public Review toEntity() {
            return Review.builder()
                    .content(content)
                    .rating(rating)
                    .userId(userId)
                    .build();
        }
    }

    /* 리뷰 수정 요청 DTO */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        private String content;
        private int rating;
    }

    /* 리뷰 응답 DTO */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String content;
        private int rating;
        private int viewCount;
        private int likeCount;
        private Long userId;

        public static Response fromEntity(Review review) {
            return Response.builder()
                    .id(review.getId())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .viewCount(review.getViewCount())
                    .likeCount(review.getLikeCount())
                    .userId(review.getUserId())
                    .build();
        }
    }
}
