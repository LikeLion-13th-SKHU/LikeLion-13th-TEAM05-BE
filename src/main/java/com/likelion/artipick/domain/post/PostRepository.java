package com.likelion.artipick.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    /** 상태로 페이징 조회 (예: ACTIVE 만) */
    Page<Post> findAllByStatus(Status status, Pageable pageable);

    /** 삭제가 아닌 글만 단건 조회 (소프트 삭제 무시) */
    Optional<Post> findByIdAndStatusNot(Long id, Status status);
}
