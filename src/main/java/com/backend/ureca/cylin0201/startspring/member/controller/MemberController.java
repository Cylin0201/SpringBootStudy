package com.backend.ureca.cylin0201.startspring.member.controller;

import com.backend.ureca.cylin0201.startspring.domain.Post;
import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.member.dto.LoginDto;
import com.backend.ureca.cylin0201.startspring.member.dto.MemberResponse;
import com.backend.ureca.cylin0201.startspring.post.dto.PostResponse;
import com.backend.ureca.cylin0201.startspring.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @ResponseBody
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable Long id) {
        Member member = memberService.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 멤버입니다."));
        return ResponseEntity.ok()
                .body(member.from());
    }

    @ResponseBody
    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> findAll() {
        List<MemberResponse> memberList = memberService.findAll()
                .stream()
                .map(Member::from)
                .toList();
        return ResponseEntity.ok(memberList);
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // login.html 반환
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto, HttpSession session) {
        Member member = memberService.findByUserName(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 멤버 아이디가 없습니다."));

        if (!memberService.matchesPassword(dto.getPassword(), member.getPassword())) {
            return ResponseEntity.badRequest().body("로그인 실패");
        }
        // 로그인 성공 → 세션에 사용자 정보 저장
        session.setAttribute("loginMember", member);
        return ResponseEntity.ok("로그인 완료입니다.");
    }

    @GetMapping("/join")
    public String joinForm() {
        return "join"; // login.html 반환
    }

    @PostMapping("/members")
    public String join(@ModelAttribute LoginDto dto) {
        memberService.join(dto);
        return "redirect:/login";
    }

    @ResponseBody
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Post> posts = memberService.getAllPosts(loginMember.getId());

        return ResponseEntity.ok(
                posts.stream()
                        .map(post -> new PostResponse(
                                post.getId(),
                                post.getTitle(),
                                post.getContent(),
                                loginMember.getUsername()))
                        .toList()
        );
    }

    @ResponseBody
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화 → 로그아웃
        return ResponseEntity.ok("로그아웃 완료");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
