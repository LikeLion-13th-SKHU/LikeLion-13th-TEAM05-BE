package com.likelion.artipick.domain.post;

import com.likelion.artipick.domain.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 제목 (필수, 최대 200자) */
    @Column(nullable = false, length = 200)
    private String title;

    /** 본문 (선택) */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    /** 카테고리 연관관계 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /** 작성자 ID */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** 상태값 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    /** 조회수 */
    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    /** 좋아요 수 */
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    /** 위도, 경도 */
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    /** 추가 정보 */
    @Column(name = "start_date", length = 50)
    private String startDate;

    @Column(name = "end_date", length = 50)
    private String endDate;

    @Column(length = 200)
    private String place;

    @Column(length = 100)
    private String area;

    @Column(length = 100)
    private String sigungu;

    @Column(length = 50)
    private String price;

    @Column(length = 50)
    private String phone;

    @Column(name = "img_url", length = 500)
    private String imgUrl;

    @Column(name = "place_url", length = 500)
    private String placeUrl;

    @Column(length = 100)
    private String seq;

    @Column(length = 500)
    private String url;

    @Column(name = "place_addr", length = 500)
    private String placeAddr;

    @Column(name = "gps_x", length = 100)
    private String gpsX;

    @Column(name = "gps_y", length = 100)
    private String gpsY;

    /** 생성/수정 시간 */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    private Post(String title, String content, Category category, Long userId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.userId = userId;
        this.status = Status.ACTIVE;
        this.viewCount = 0;
        this.likeCount = 0;
    }

    @PrePersist
    protected void onCreateDefaults() {
        if (this.status == null) this.status = Status.ACTIVE;
        if (this.viewCount == null) this.viewCount = 0;
        if (this.likeCount == null) this.likeCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String content, Category category, Status status) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        if (category != null) this.category = category;
        if (status != null) this.status = status;
    }

    public void increaseView() { this.viewCount += 1; }
    public void increaseLike() { this.likeCount += 1; }
    public void decreaseLike() { this.likeCount = Math.max(0, this.likeCount - 1); }
    public boolean isOwner(Long userId) { return this.userId != null && this.userId.equals(userId); }
    public void softDelete() { this.status = Status.DELETED; }
}
