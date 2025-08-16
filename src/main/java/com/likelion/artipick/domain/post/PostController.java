package com.likelion.artipick.domain.post;

import com.likelion.artipick.domain.post.dto.PostDtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        Page<Response> response = postService.getList(pageable);
        return ResponseEntity.ok(response);

    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Response> getOne(@PathVariable Long id) {
        Response response = postService.getOne(id);
        return ResponseEntity.ok(response);
    }

    // 생성
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody CreateRequest req) {
        Response response = postService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 수정
    @PatchMapping("/{id}")  // ✅ 부분 수정 고려
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody UpdateRequest req) {
        Response response = postService.update(id, req);
        return ResponseEntity.ok(response);
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

    @GetMapping("/{id}/location")
    public ResponseEntity<LocationResponse> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getLocation(id));
    }
}

