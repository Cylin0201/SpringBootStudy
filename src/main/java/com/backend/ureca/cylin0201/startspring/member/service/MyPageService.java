package com.backend.ureca.cylin0201.startspring.member.service;

import com.backend.ureca.cylin0201.startspring.comment.repository.CommentRepository;
import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.member.dto.MyPageDto;
import com.backend.ureca.cylin0201.startspring.member.repository.MemberRepository;
import com.backend.ureca.cylin0201.startspring.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //GET 마이페이지
    public MyPageDto getMyPage(String memberName){
        Member member = memberRepository.findByUsername(memberName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        return MyPageDto.builder()
                .memberName(member.getUsername())
                .postCnt(postRepository.countByMemberId(member.getId()))
                .commentCnt(commentRepository.countByMemberId(member.getId()))
                .build();
    }



}
