package com.likelion.artipick.domain.post.dto.response;

import com.likelion.artipick.domain.category.dto.CategoryDto;
import com.likelion.artipick.domain.post.Post;
import com.likelion.artipick.domain.post.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private CategoryDto category;
    private Long userId;
    private Status status;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public PostResponse(Long id, String title, String content,
                        CategoryDto category, Long userId, Status status,
                        Integer viewCount, Integer likeCount,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.userId = userId;
        this.status = status;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostResponse from(Post p) {
        return PostResponse.builder()
                .id(p.getId())
                .title(p.getTitle())
                .content(p.getContent())
                .category(CategoryDto.from(p.getCategory()))
                .userId(p.getUserId())
                .status(p.getStatus())
                .viewCount(p.getViewCount())
                .likeCount(p.getLikeCount())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
