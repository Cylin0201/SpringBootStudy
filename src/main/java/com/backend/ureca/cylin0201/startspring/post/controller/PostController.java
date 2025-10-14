package com.backend.ureca.cylin0201.startspring.post.controller;

import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.member.service.MemberService;
import com.backend.ureca.cylin0201.startspring.post.dto.PostCommentResponse;
import com.backend.ureca.cylin0201.startspring.post.dto.PostRequest;
import com.backend.ureca.cylin0201.startspring.post.dto.UpdatePostRequest;
import com.backend.ureca.cylin0201.startspring.post.service.PostService;
import com.backend.ureca.cylin0201.startspring.post.dto.PostResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/posts/new")
    public String newPostForm(Model model, HttpSession session) {
        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (context == null || context.getAuthentication() == null) {
            return "redirect:/login";
        }
        String username = context.getAuthentication().getName();
        Member loginMember = memberService.findByUserName(username).orElse(null);
        if (loginMember == null) {
            return "redirect:/login";
        }

        model.addAttribute("memberId", loginMember.getId());
        model.addAttribute("postRequest", new PostRequest(loginMember.getId(), "", ""));
        return "post/post_form";
    }

    @GetMapping("/posts")
    public String listPosts(@RequestParam(required = false) String keyword,
                            @RequestParam(defaultValue = "memberName") String type,
                            Model model, HttpSession session) {

        List<PostResponse> posts;

        if (keyword == null || keyword.trim().isEmpty()) {
            // 검색어가 없을 경우 전체 게시글 조회
            posts = postService.getAllPosts();
        } else {
            // 검색어가 있을 경우
            if ("titleContent".equals(type)) {
                posts = postService.searchPostsByTitleOrContent(keyword);
            } else {
                posts = postService.searchPostsByMemberName(keyword);
            }
        }

        String memberName = (String) session.getAttribute("memberName");
        model.addAttribute("posts", posts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        model.addAttribute("memberName", memberName); // 세션 정보가 있다면 가져와서 넣기

        return "post/posts"; // templates/posts.html 렌더링
    }

    @PostMapping("/posts")
    public String createPost(@ModelAttribute PostRequest postRequest) {
        postService.uploadPost(postRequest);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{postId}")
    public String getDetailPost(@PathVariable("postId") Long postId, Model model, HttpSession session) {
        PostCommentResponse res = postService.getPostComments(postId);
        model.addAttribute("post", res);

        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (context == null || context.getAuthentication() == null) {
            return "redirect:/login";
        }

        String username = context.getAuthentication().getName();
        Member loginMember = memberService.findByUserName(username).orElse(null);

        boolean canDelete = loginMember != null && loginMember.getUsername().equalsIgnoreCase(res.getMemberName());
        model.addAttribute("canDelete", canDelete);

        model.addAttribute("comments", res.getComments());

        return "post/post_detail";
    }


    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable Long postId, HttpSession session) {
        PostResponse post = postService.getPostById(postId);
        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        String username = context.getAuthentication().getName();

        if (context == null || context.getAuthentication() == null) {
            return "redirect:/login";
        }

        if (username == null || !username.equals(post.getMemberName())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        postService.deletePost(postId);
        return "redirect:/posts";
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
