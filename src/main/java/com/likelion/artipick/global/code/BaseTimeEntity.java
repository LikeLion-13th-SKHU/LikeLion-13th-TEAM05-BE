package com.likelion.artipick.global.code;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 모든 Entity 생성,수정일을 자동으로 관리하는 추상 클래스
 * - @MappedSuperclass: 상속 시 필드도 컬럼으로 인식
 * - @EntityListeners: Auditing 기능 활성화
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    /*생성 시각 */
    @CreatedDate
    private LocalDateTime createdAt;

    /*수정 시각 */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
