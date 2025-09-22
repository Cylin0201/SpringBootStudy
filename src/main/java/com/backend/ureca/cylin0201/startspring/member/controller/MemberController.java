package com.backend.ureca.cylin0201.startspring.member.controller;

import com.backend.ureca.cylin0201.startspring.domain.Post;
import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.member.dto.LoginDto;
import com.backend.ureca.cylin0201.startspring.post.dto.PostResponse;
import com.backend.ureca.cylin0201.startspring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Member> join(@RequestBody LoginDto loginDto) {
        Member member = loginDto.toEntity();
        memberService.join(member);

        return ResponseEntity.ok()
                .body(member);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Optional<Member>> findById(@PathVariable Long id) {
        Optional<Member> member = memberService.findById(id);
        return ResponseEntity.ok()
                .body(member);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> findAll() {
        List<Member> memberList = memberService.findAll();
        return ResponseEntity.ok(memberList);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        Optional<Member> member = memberService.findByUserName(dto.userName);
        if (member.isEmpty()) throw new RuntimeException();

        if (member.get().getPassword().equals(dto.password))
            return ResponseEntity.ok("로그인 완료입니다.");
        else return ResponseEntity.badRequest().body("로그인 실패");
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestParam Long id) {
        Member member = memberService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 없거나 잘못된 id"));

        List<Post> posts = memberService.getAllPosts(id);

        return ResponseEntity.ok()
                .body(posts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        member.getUsername()
                ))
                .toList()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
