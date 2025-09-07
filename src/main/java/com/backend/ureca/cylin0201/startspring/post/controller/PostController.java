package com.backend.ureca.cylin0201.startspring.post.controller;

import com.backend.ureca.cylin0201.startspring.post.dto.PostRequest;
import com.backend.ureca.cylin0201.startspring.post.dto.UpdatePostRequest;
import com.backend.ureca.cylin0201.startspring.post.service.PostService;
import com.backend.ureca.cylin0201.startspring.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    ResponseEntity<PostResponse> uploadPost(@RequestBody PostRequest req){
        return ResponseEntity.ok(postService.uploadPost(req));
    }

    @GetMapping("/posts/{postId}")
    ResponseEntity<PostResponse> getPstById(@PathVariable Long postId){
        return ResponseEntity.ok()
                .body(postService.getPostById(postId));
    }

    @DeleteMapping("/posts/{postId}")
    ResponseEntity<String> deletePost(@PathVariable Long postId){
        return ResponseEntity.ok()
                .body("포스트가 삭제되었습니다." + postService.deletePost(postId));
    }

    @PutMapping("/posts")
    ResponseEntity<PostResponse> updatePost(@RequestBody UpdatePostRequest req){
        return ResponseEntity.ok()
                .body(postService.updatePost(req));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
