package com.likelion.artipick.domain.post.dto;

import com.likelion.artipick.domain.post.Post;
import com.likelion.artipick.domain.post.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long categoryId;
    private Long userId;
    private Status status;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse from(Post p) {
        return new PostResponse(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                p.getCategoryId(),
                p.getUserId(),
                p.getStatus(),
                p.getViewCount(),
                p.getLikeCount(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
