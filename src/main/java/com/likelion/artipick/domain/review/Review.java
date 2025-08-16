package com.likelion.artipick.domain.review;

import com.likelion.artipick.domain.post.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 내용 */
    @Column(nullable = false, length = 1000)
    private String content;

    /** 별점 (1~5) */
    @Column(nullable = false)
    private int rating;

    /** 조회수 */
    @Column(nullable = false)
    private int viewCount = 0;

    /** 좋아요 수 */
    @Column(nullable = false)
    private int likeCount = 0;

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;

    /** 작성자 ID */
    @Column(nullable = false)
    private Long userId;

    /** 생성/수정 시간 */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ===== 생성자 & 팩토리 =====
    protected Review(String content, int rating, Long userId) {
        this.content = content;
        this.rating = rating;
        this.userId = userId;
    }
    public static Review of(String content, int rating, Long userId) {
        return new Review(content, rating, userId);
    }

    // ===== 비즈니스 메서드 =====
    public void update(String content, Integer rating) {
        if (content != null) this.content = content;
        if (rating != null) this.rating = rating;
    }
    public void softDelete() { this.status = Status.DELETED; }
    public void increaseView() { this.viewCount += 1; }
    public void increaseLike() { this.likeCount += 1; }
    public void decreaseLike() { this.likeCount = Math.max(0, this.likeCount - 1); }

    // ===== JPA 라이프사이클 =====
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
