package com.backend.ureca.cylin0201.startspring.service;

import com.backend.ureca.cylin0201.startspring.model.Member;
import com.backend.ureca.cylin0201.startspring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> getMembers(){
        return memberRepository.findAll();
    }
}
