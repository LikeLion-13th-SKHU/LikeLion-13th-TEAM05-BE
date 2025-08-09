package com.likelion.artipick.domain.post;

import com.likelion.artipick.global.code.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 게시글 엔티티
 * - JPA가 이 클래스를 기반으로 테이블을 만들고(ddl-auto: update), CRUD를 수행함
 * - BaseTimeEntity: createdAt/updatedAt 자동 관리
 */
@Entity
@Table(name = "posts") // 테이블명을 posts로 고정 (기본값 : post)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 프록시용 기본 생성자 보호
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 제목 (필수, 최대 200자) */
    @Column(nullable = false, length = 200)
    private String title;

    /** 본문 (선택) */
    @Lob
    private String content;

    /** 카테고리 ID */
    @Column(name = "category_id")
    private Long categoryId;

    /** 작성자 ID (필수) */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**상태값 (Status) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    /** 조회수 (기본 0) */
    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    /** 좋아요 수 (기본 0) */
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

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
    public void softDelete() { this.status = Status.DELETED; }
}
