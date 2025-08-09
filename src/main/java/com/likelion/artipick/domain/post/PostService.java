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

    /** 게시글 생성 */
    public Response create(CreateRequest req) {
        Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .categoryId(req.getCategoryId())
                .userId(req.getUserId()) // 인증 붙으면 SecurityContext에서 꺼내 쓰도록 변경
                .build();

        Post saved = postRepository.save(post);
        return Response.from(saved);
    }

    /** 단건 조회(삭제 글 제외) + 조회수 증가 */
    public Response getOne(Long id, boolean increaseView) {
        Post post = postRepository.findByIdAndStatusNot(id, Status.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));

        if (increaseView) post.increaseView();
        return Response.from(post);
    }

    /** 목록 조회 (기본 ACTIVE) */
    @Transactional(readOnly = true)
    public Page<Response> getList(Pageable pageable) {
        return postRepository.findAllByStatus(Status.ACTIVE, pageable)
                .map(Response::from);
    }

    /** 수정 (작성자 검증은 추후 인증 붙고 나서) */
    public Response update(Long id, UpdateRequest req) {
        Post post = postRepository.findByIdAndStatusNot(id, Status.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));

        post.update(req.getTitle(), req.getContent(), req.getCategoryId(), req.getStatus());
        return Response.from(post);
    }

    /** 소프트 삭제 */
    public void delete(Long id) {
        Post post = postRepository.findByIdAndStatusNot(id, Status.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));
        post.softDelete();
    }
}
