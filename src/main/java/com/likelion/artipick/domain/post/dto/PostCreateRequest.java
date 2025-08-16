package com.likelion.artipick.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
    @NotBlank
    private String title;
    private String content;      // 선택

    private Long categoryId;     // 선택

    @NotNull
    private Long userId;         // 필수(작성자)
}
