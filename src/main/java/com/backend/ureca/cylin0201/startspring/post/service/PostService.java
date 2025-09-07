package com.backend.ureca.cylin0201.startspring.post.service;

import com.backend.ureca.cylin0201.startspring.post.domain.Post;
import com.backend.ureca.cylin0201.startspring.post.dto.PostRequest;
import com.backend.ureca.cylin0201.startspring.post.repository.PostRepository;
import com.backend.ureca.cylin0201.startspring.user.domain.Member;
import com.backend.ureca.cylin0201.startspring.user.dto.PostResponse;
import com.backend.ureca.cylin0201.startspring.user.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponse uploadPost(PostRequest postRequest) {
        Member member = memberRepository.findById(postRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + postRequest.getMemberId()));

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .member(member)
                .build();

        Post saved = postRepository.save(post);

        return new PostResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                member.getUserName()
        );
    }
}