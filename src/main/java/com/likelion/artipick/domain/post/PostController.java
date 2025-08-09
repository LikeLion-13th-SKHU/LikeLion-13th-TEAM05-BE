package com.likelion.artipick.domain.post;

import com.likelion.artipick.domain.post.dto.PostDtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /** 생성 */
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody @Valid CreateRequest req) {
        return ResponseEntity.ok(postService.create(req));
    }

    /** 단건 조회 (조회수 증가 기본 true) */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getOne(@PathVariable Long id,
                                           @RequestParam(defaultValue = "true") boolean increaseView) {
        return ResponseEntity.ok(postService.getOne(id, increaseView));
    }

    /** 목록(페이지네이션) 예: /api/posts?page=0&size=10&sort=id,desc */
    @GetMapping
    public ResponseEntity<Page<Response>> list(Pageable pageable) {
        return ResponseEntity.ok(postService.getList(pageable));
    }

    /** 수정 */
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id,
                                           @RequestBody @Valid UpdateRequest req) {
        return ResponseEntity.ok(postService.update(id, req));
    }

    /** 삭제(소프트) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
