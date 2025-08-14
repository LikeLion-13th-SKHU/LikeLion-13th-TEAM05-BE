package com.likelion.artipick.domain.post;

import com.likelion.artipick.global.code.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;




/**
 * 게시글 엔티티
 * - JPA가 이 클래스를 기반으로 테이블을 만들고(ddl-auto: update), CRUD 수행
 * - BaseTimeEntity: createdAt/updatedAt 자동 관리
 */
@Entity
@Table(name = "posts") // 테이블명을 posts로 고정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 프록시용 기본 생성자 보호
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL AUTO_INCREMENT
    private Long id;

    /** 제목 (필수, 최대 200자) */
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * 본문 (선택)
     * - @Lob: 긴 텍스트 저장
     * - LAZY: 목록 조회 시 본문을 매번 로딩하지 않도록 (성능)
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    /** 카테고리 ID (추후 Category 엔티티로 교체 예정) */
    @Column(name = "category_id")
    private Long categoryId;

    /** 작성자 ID (필수) */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** 상태값 (Status) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    /** 조회수 (기본 0) */
    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    /** 좋아요 수 (기본 0) */
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    /** 위도, 경도 */
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;


    // ===== 선택/추가 필드 (오픈 API 매핑 대비) =====
    private String startDate;
    private String endDate;
    private String place;
    private String area;
    private String sigungu;
    private String price;
    private String phone;
    private String imgUrl;
    private String placeUrl;
    private String seq;       // 외부 전시 ID 등
    private String url;       // 관련 링크
    private String placeAddr; // 장소 상세 주소
    private String gpsX;
    private String gpsY;

    @Builder
    private Post(String title, String content, Long categoryId, Long userId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.userId = userId;
        this.status = Status.ACTIVE;
        this.viewCount = 0;
        this.likeCount = 0;
    }

    /** 저장 전 기본값 안전장치 (빌더 미사용 케이스 대비) */
    @PrePersist
    protected void onCreateDefaults() {
        if (this.status == null) this.status = Status.ACTIVE;
        if (this.viewCount == null) this.viewCount = 0;
        if (this.likeCount == null) this.likeCount = 0;
    }

    /** 일부 필드만 수정 (null은 무시) */
    public void update(String title, String content, Long categoryId, Status status) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        if (categoryId != null) this.categoryId = categoryId;
        if (status != null) this.status = status;
    }

    /** 조회수 +1 */
    public void increaseView() { this.viewCount += 1; }

    /** 좋아요 +1 */
    public void increaseLike() { this.likeCount += 1; }

    /** 좋아요 -1 (최소 0) */
    public void decreaseLike() { this.likeCount = Math.max(0, this.likeCount - 1); }

    /** 작성자 본인 여부 */
    public boolean isOwner(Long userId) {
        return this.userId != null && this.userId.equals(userId);
    }

    /** 소프트 삭제 (DB 레코드는 남기고 상태만 변경) */
    public void softDelete() {
        this.status = Status.DELETED;
    }

}
