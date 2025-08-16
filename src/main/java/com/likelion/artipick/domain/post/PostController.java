package com.likelion.artipick.domain.post;

import com.likelion.artipick.domain.post.dto.PostDtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /* 게시글 CRUD */

    // 목록
    @GetMapping
    public ResponseEntity<Page<Response>> list(Pageable pageable) {
        return ResponseEntity.ok(postService.getList(pageable));
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Response> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getOne(id));
    }

    // 생성
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody CreateRequest req) {
        return ResponseEntity.ok(postService.create(req));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody UpdateRequest req) {
        return ResponseEntity.ok(postService.update(id, req));
    }

    // 삭제(소프트)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /* 조회수, 좋아요 */

    // 조회수 +1
    @PostMapping("/{id}/view")
    public ResponseEntity<Response> increaseView(@PathVariable Long id) {
        return ResponseEntity.ok(postService.increaseView(id));
    }

    // 좋아요 +1
    @PostMapping("/{id}/like")
    public ResponseEntity<Response> like(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(postService.like(id, userId));
    }

    // 좋아요 -1
    @DeleteMapping("/{id}/like")
    public ResponseEntity<Response> unlike(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(postService.unlike(id, userId));
    }

    /* 위치 */

    // 위도·경도
    @GetMapping("/{id}/location")
    public ResponseEntity<LocationResponse> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getLocation(id));
    }
}
