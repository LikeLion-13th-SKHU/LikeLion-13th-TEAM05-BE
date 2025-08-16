package com.likelion.artipick.domain.post.dto;

import com.likelion.artipick.domain.post.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {
    // 전부 선택 값. null이면 서비스에서 기존 값 유지
    private String title;
    private String content;
    private Long categoryId;
    private Status status;   // ACTIVE/DELETED 등
}
