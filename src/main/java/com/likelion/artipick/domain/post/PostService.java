package com.likelion.artipick.domain.post;

import com.likelion.artipick.domain.post.dto.PostDtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    /** 게시글 생성 */
    public Response create(CreateRequest req) {
        Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .categoryId(req.getCategoryId())
                .userId(req.getUserId()) // TODO: 인증 붙으면 SecurityContext에서 꺼내기
                .build();

        Post saved = postRepository.save(post);
        return Response.from(saved);
    }

    /** 단건 조회(삭제 글 제외) */
    @Transactional(readOnly = true)
    public Response getOne(Long id) {
        return Response.from(getActivePost(id));
    }

    /** 목록 조회 (기본 ACTIVE) */
    @Transactional(readOnly = true)
    public Page<Response> getList(Pageable pageable) {
        return postRepository.findAllByStatus(Status.ACTIVE, pageable)
                .map(Response::from);
    }

    /** 수정 */
    public Response update(Long id, UpdateRequest req) {
        Post post = getActivePost(id);
        post.update(req.getTitle(), req.getContent(), req.getCategoryId(), req.getStatus());
        return Response.from(post);
    }

    /** 소프트 삭제 */
    public void delete(Long id) {
        Post post = getActivePost(id);
        post.softDelete();
    }

    /** 조회수 +1 */
    public Response increaseView(Long id) {
        Post post = getActivePost(id);
        post.increaseView();
        return Response.from(post);
    }

    /** 좋아요 (중복 방지) */
    public Response like(Long postId, Long userId) {
        Post post = getActivePost(postId);

        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new IllegalStateException("userId=" + userId + "는 이미 postId=" + postId + "에 좋아요를 눌렀습니다.");
        }

        post.increaseLike();
        postLikeRepository.save(new PostLike(postId, userId));
        return Response.from(post);
    }

    /** 좋아요 취소 */
    public Response unlike(Long postId, Long userId) {
        Post post = getActivePost(postId);

        postLikeRepository.findByPostIdAndUserId(postId, userId)
                .ifPresentOrElse(like -> {
                    post.decreaseLike();
                    postLikeRepository.delete(like);
                }, () -> {
                    throw new IllegalStateException("좋아요 상태가 아닙니다.");
                });

        return Response.from(post);
    }

    /** 위도·경도 조회 */
    @Transactional(readOnly = true)
    public LocationResponse getLocation(Long id) {
        Post post = getActivePost(id);
        if (post.getLatitude() == null || post.getLongitude() == null) {
            throw new IllegalStateException("위치 정보가 없는 게시글입니다.");
        }
        return LocationResponse.from(post);
    }

    /** 공통: ACTIVE 상태 게시글 조회 */
    private Post getActivePost(Long id) {
        return postRepository.findByIdAndStatusNot(id, Status.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));
    }
}
