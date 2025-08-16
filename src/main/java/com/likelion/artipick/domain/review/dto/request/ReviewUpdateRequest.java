package com.likelion.artipick.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ReviewUpdateRequest(
        @Size(max = 1000) String content,
        @Min(1) @Max(5) Integer rating
) {}
