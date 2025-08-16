package com.likelion.artipick.domain.review.dto.request;

import com.likelion.artipick.domain.review.Review;
import jakarta.validation.constraints.*;

public record ReviewCreateRequest(
        @NotBlank @Size(max = 1000) String content,
        @Min(1) @Max(5) int rating,
        @NotNull Long userId
) {
    public Review toEntity() {
        return Review.of(content, rating, userId);
    }
}
