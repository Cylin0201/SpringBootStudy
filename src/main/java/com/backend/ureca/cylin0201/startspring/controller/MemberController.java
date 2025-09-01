package com.backend.ureca.cylin0201.startspring.controller;

import com.backend.ureca.cylin0201.startspring.model.Member;
import com.backend.ureca.cylin0201.startspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/member/list")
    public List<Member> getAllMembers(){
        List<Member> members = memberService.getMembers();
        return members;
    }
}
