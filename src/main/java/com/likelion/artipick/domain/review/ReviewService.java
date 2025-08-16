package com.likelion.artipick.domain.review;

import com.likelion.artipick.domain.review.dto.ReviewDtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /* 리뷰 목록 (페이징) */
    @Transactional(readOnly = true)
    public Page<Response> getList(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(Response::fromEntity);
    }

    /* 단건 조회 */
    @Transactional(readOnly = true)
    public Response getReview(Long id) {
        return Response.fromEntity(reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다.")));
    }

    /* 리뷰 생성 */
    @Transactional
    public Response createReview(CreateRequest req) {
        Review review = req.toEntity();
        return Response.fromEntity(reviewRepository.save(review));
    }

    /* 리뷰 수정 */
    @Transactional
    public Response updateReview(Long id, UpdateRequest req) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        review.setContent(req.getContent());
        review.setRating(req.getRating());
        return Response.fromEntity(review);
    }

    /* 리뷰 삭제 */
    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        reviewRepository.delete(review);
    }
}
