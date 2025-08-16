package com.likelion.artipick.domain.review;

import com.likelion.artipick.domain.post.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByStatus(Status status, Pageable pageable);
    Optional<Review> findByIdAndStatusNot(Long id, Status status);
}
