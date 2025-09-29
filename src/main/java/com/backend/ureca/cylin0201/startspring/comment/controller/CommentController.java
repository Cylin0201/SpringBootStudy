package com.backend.ureca.cylin0201.startspring.comment.controller;

import com.backend.ureca.cylin0201.startspring.comment.dto.CommentRequest;
import com.backend.ureca.cylin0201.startspring.comment.dto.CommentResponse;
import com.backend.ureca.cylin0201.startspring.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<Void> postComment(@RequestBody CommentRequest request) {
        commentService.postComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody String content) {
        commentService.updateComment(commentId, content);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    // 단일 댓글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
        CommentResponse response = commentService.getComment(commentId);
        return ResponseEntity.ok(response);
    }

    // 게시글에 달린 모든 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponse> responses = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(responses);
    }
}
