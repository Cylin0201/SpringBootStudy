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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입 폼 화면
    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("loginDto", new LoginDto()); // 폼에 바인딩할 객체
        return "join";
    }

    // 회원가입 처리
    @PostMapping("/members")
    public String signup(@ModelAttribute LoginDto loginDto) {
        memberService.join(loginDto);
        return "redirect:/login";
    }

    // 로그인 폼 페이지
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login"; // login.html
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, HttpSession session, Model model) {
        Member member = memberService.findByUserName(loginDto.getUsername()).orElse(null);

        if (member == null || !memberService.matchesPassword(loginDto.getPassword(), member.getPassword())) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }

        // 1. 로그인 성공 시 SecurityContextHolder에 인증 정보 등록
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. HttpSession에도 보관 (시큐리티 세션 동기화)
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/";
    }


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
