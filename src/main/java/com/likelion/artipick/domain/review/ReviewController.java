package com.likelion.artipick.domain.review;

import com.likelion.artipick.domain.review.dto.ReviewDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<ReviewDtos.Response> createReview(@RequestBody ReviewDtos.CreateRequest request) {
        return ResponseEntity.ok(reviewService.createReview(request));
    }

    // 리뷰 목록 조회 (페이징)
    @GetMapping
    public ResponseEntity<Page<ReviewDtos.Response>> getAllReviews(Pageable pageable) {
        return ResponseEntity.ok(reviewService.getList(pageable));
    }

    // 리뷰 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDtos.Response> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDtos.Response> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewDtos.UpdateRequest request
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
