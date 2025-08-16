package com.likelion.artipick.domain.post.dto;

import com.likelion.artipick.domain.category.dto.CategoryDto;
import com.likelion.artipick.domain.post.Post;
import com.likelion.artipick.domain.post.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDtos {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        @NotBlank
        @Size(max = 200)
        private String title;

        private String content;
        private Long categoryId;   // 요청 시에는 단순히 categoryId만 받음

        /** 임시로 작성자 ID 받기 */
        private Long userId;

        @Builder
        public CreateRequest(String title, String content, Long categoryId, Long userId) {
            this.title = title;
            this.content = content;
            this.categoryId = categoryId;
            this.userId = userId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        @Size(max = 200)
        private String title;

        private String content;
        private Long categoryId;
        private Status status;

        @Builder
        public UpdateRequest(String title, String content, Long categoryId, Status status) {
            this.title = title;
            this.content = content;
            this.categoryId = categoryId;
            this.status = status;
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private CategoryDto category;
        private Long userId;
        private Status status;
        private Integer viewCount;
        private Integer likeCount;
        private String createdAt;
        private String updatedAt;

        public static Response from(Post p) {
            return Response.builder()
                    .id(p.getId())
                    .title(p.getTitle())
                    .content(p.getContent())
                    .category(CategoryDto.from(p.getCategory())) // Category 엔티티를 DTO 변환
                    .userId(p.getUserId())
                    .status(p.getStatus())
                    .viewCount(p.getViewCount())
                    .likeCount(p.getLikeCount())
                    .createdAt(p.getCreatedAt() != null ? p.getCreatedAt().toString() : null)
                    .updatedAt(p.getUpdatedAt() != null ? p.getUpdatedAt().toString() : null)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class LocationResponse {
        private String gpsX; // 경도
        private String gpsY; // 위도

        public static LocationResponse from(Post post) {
            return new LocationResponse(post.getGpsX(), post.getGpsY());
        }
    }
}
