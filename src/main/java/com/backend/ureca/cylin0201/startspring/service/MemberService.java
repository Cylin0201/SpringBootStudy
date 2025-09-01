package com.backend.ureca.cylin0201.startspring.service;

import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.dto.LoginDto;
import com.backend.ureca.cylin0201.startspring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public String signUp(LoginDto loginDto){
        Member member = memberRepository.save(new Member(loginDto.getId(), loginDto.getPassword()));
        return member.getId();
    }

    public Boolean logIn(LoginDto loginDto) throws Exception {
        Member member = memberRepository.findById(loginDto.getId())
                .orElseThrow(()-> new Exception("아이디가 없어용"));
        return member.getPassword().equals(loginDto.getPassword());
    }
}
