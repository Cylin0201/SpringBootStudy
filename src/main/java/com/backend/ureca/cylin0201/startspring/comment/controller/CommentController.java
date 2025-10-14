package com.backend.ureca.cylin0201.startspring.comment.controller;

import com.backend.ureca.cylin0201.startspring.comment.dto.CommentRequest;
import com.backend.ureca.cylin0201.startspring.comment.dto.CommentResponse;
import com.backend.ureca.cylin0201.startspring.comment.service.CommentService;
import com.backend.ureca.cylin0201.startspring.domain.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성 (일반 댓글 + 대댓글)
    @PostMapping
    public String postComment(
            @PathVariable Long postId,
            @RequestParam(required = false) Long parentId,
            @RequestParam String content,
            HttpSession session) {

        // 로그인 유저 정보 가져오기
        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (context == null || context.getAuthentication() == null) {
            return "redirect:/login";
        }

        String username = context.getAuthentication().getName();

        CommentRequest req = new CommentRequest(
                username,           // memberId는 서비스에서 username으로 매핑
                postId,
                parentId,
                content
        );

        commentService.postComment(req);
        return "redirect:/posts/" + postId;
    }

     //댓글 수정 (Ajax 요청 or 폼 요청 모두 대응 가능)
    @PutMapping("/{commentId}")
    @ResponseBody
    public ResponseEntity<Void> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody String content) {
        commentService.updateComment(commentId, content);
        return ResponseEntity.ok().build();
    }

    //댓글 삭제
    @PostMapping("/{commentId}/delete")
    public String deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }

    /**
     * 💬 게시글별 댓글 조회 (AJAX 사용 시)
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponse> responses = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(responses);
    }
}
