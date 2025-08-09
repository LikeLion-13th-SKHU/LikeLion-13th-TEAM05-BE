package com.likelion.artipick.domain.post;

/** 게시글 상태 */
public enum Status {
    ACTIVE,   // 정상 노출
    HIDDEN,   // 비공개/임시 숨김
    DELETED   // 소프트 삭제
}
