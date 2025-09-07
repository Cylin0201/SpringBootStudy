package com.backend.ureca.cylin0201.startspring.user.service;

import com.backend.ureca.cylin0201.startspring.post.domain.Post;
import com.backend.ureca.cylin0201.startspring.post.repository.PostRepository;
import com.backend.ureca.cylin0201.startspring.user.domain.Member;
import com.backend.ureca.cylin0201.startspring.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(Member member){
        memberRepository.save(member);
    }

    public Optional<Member> findById(Long id){
        return memberRepository.findById(id);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Optional<Member> findByUserName(String userName){
        return memberRepository.findByUserName(userName);
    }

    //멤버의 작성된 포스트 조회
    public List<Post> getAllPosts(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id가 잘못되거나 해당 유저가 없음."));
        return member.getPosts();
    }
}
