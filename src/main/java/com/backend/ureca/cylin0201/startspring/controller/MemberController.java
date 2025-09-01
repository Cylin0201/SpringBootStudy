package com.backend.ureca.cylin0201.startspring.controller;

import com.backend.ureca.cylin0201.startspring.dto.LoginDto;
import com.backend.ureca.cylin0201.startspring.repository.MemberRepository;
import com.backend.ureca.cylin0201.startspring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody LoginDto dto){
        if (memberRepository.existsById(dto.getId()))
            throw new IllegalArgumentException("이미 아이디가 있음");

        return ResponseEntity.ok()
                .body(memberService.signUp(dto));
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> logIn(@RequestBody LoginDto dto) throws Exception {
        if (!memberRepository.existsById(dto.getId()))
            throw new Exception("아이디 없음");
        if (memberService.logIn(dto))
            return ResponseEntity.ok()
                    .body(dto.getId());
        else throw new Exception("비밀번호 틀림");
    }
}
