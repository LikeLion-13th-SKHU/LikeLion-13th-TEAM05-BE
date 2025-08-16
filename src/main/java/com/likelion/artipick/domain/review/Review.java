package com.likelion.artipick.domain.review;

import com.likelion.artipick.domain.post.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 리뷰 내용
    @Column(nullable = false, length = 1000)
    private String content;

    // 별점 (1~5)
    @Column(nullable = false)
    private int rating;

    // 조회수
    @Column(nullable = false)
    private int viewCount = 0;

    // 좋아요 수
    @Column(nullable = false)
    private int likeCount = 0;

    // 상태 (ACTIVE, DELETED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    // 작성자 ID
    @Column(nullable = false)
    private Long userId;

    // 작성/수정 시간
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // 조회수 증가
    public void increaseView() {
        this.viewCount++;
    }

    // 좋아요 증가
    public void increaseLike() {
        this.likeCount++;
    }

    // 좋아요 감소
    public void decreaseLike() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
