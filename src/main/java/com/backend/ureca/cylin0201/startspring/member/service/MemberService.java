package com.backend.ureca.cylin0201.startspring.member.service;

import com.backend.ureca.cylin0201.startspring.domain.Post;
import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.member.dto.LoginDto;
import com.backend.ureca.cylin0201.startspring.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(LoginDto loginDto){
        Member member = Member.builder()
                .username(loginDto.getUsername())
                .password(passwordEncoder.encode(loginDto.getPassword()))
                .role("ROLE_USER")
                .build();
        memberRepository.save(member);
    }

    public Optional<Member> findById(Long id){
        return memberRepository.findById(id);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Optional<Member> findByUserName(String username){
        return memberRepository.findByUsername(username);
    }

    //멤버의 작성된 포스트 조회
    public List<Post> getAllPosts(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id가 잘못되거나 해당 유저가 없음."));
        return member.getPosts();
    }

    public boolean matchesPassword(String raw, String encoded){
        return passwordEncoder.matches(raw, encoded);
    }
}
