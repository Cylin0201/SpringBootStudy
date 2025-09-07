package com.backend.ureca.cylin0201.startspring.post.service;

import com.backend.ureca.cylin0201.startspring.post.domain.Post;
import com.backend.ureca.cylin0201.startspring.post.dto.PostRequest;
import com.backend.ureca.cylin0201.startspring.post.dto.UpdatePostRequest;
import com.backend.ureca.cylin0201.startspring.post.repository.PostRepository;
import com.backend.ureca.cylin0201.startspring.user.domain.Member;
import com.backend.ureca.cylin0201.startspring.user.dto.PostResponse;
import com.backend.ureca.cylin0201.startspring.user.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //포스트 작성
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
                post.getMember().getUserName()
        );
    }

    //포스트 조회
    public PostResponse getPostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Post입니다."));

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getUserName()
        );
    }

    //포스트 업데이트
    public PostResponse updatePost(UpdatePostRequest request){
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Post입니다."));

        Post newPost = Post.builder()
                .member(post.getMember())
                .title(request.getTitle())
                .content(request.getContent())
                .updatedAt(LocalDateTime.now())
                .createdAt(post.getCreatedAt())
                .build();
        postRepository.deleteById(post.getId());

        postRepository.save(newPost);

        return new PostResponse(
                newPost.getId(),
                newPost.getTitle(),
                newPost.getContent(),
                newPost.getMember().getUserName());
    }

    //포스트 삭제
    public Long deletePost(Long postId){
        postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Post입니다."));

        postRepository.deleteById(postId);
        return postId;
    }

}