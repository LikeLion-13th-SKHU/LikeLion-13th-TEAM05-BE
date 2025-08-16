package com.likelion.artipick.domain.review;

import com.likelion.artipick.domain.post.Status;
import com.likelion.artipick.domain.review.dto.request.ReviewCreateRequest;
import com.likelion.artipick.domain.review.dto.request.ReviewUpdateRequest;
import com.likelion.artipick.domain.review.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /** 목록(활성만) */
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getList(Pageable pageable) {
        return reviewRepository.findAllByStatus(Status.ACTIVE, pageable)
                .map(ReviewResponse::from);
    }

    /** 단건 조회(삭제 제외) */
    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long id) {
        return ReviewResponse.from(getActive(id));
    }

    /** 생성 */
    public ReviewResponse createReview(ReviewCreateRequest req) {
        Review saved = reviewRepository.save(req.toEntity());
        return ReviewResponse.from(saved);
    }

    /** 수정 (부분 수정 허용) */
    public ReviewResponse updateReview(Long id, ReviewUpdateRequest req) {
        Review review = getActive(id);
        review.update(req.content(), req.rating());
        return ReviewResponse.from(review);
    }

    /** 삭제(소프트) */
    public void deleteReview(Long id) {
        Review review = getActive(id);
        review.softDelete();
    }

    // 공통 조회
    private Review getActive(Long id) {
        return reviewRepository.findByIdAndStatusNot(id, Status.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + id));
    }
}
