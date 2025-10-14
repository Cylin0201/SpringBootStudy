package com.backend.ureca.cylin0201.startspring.post.service;

import com.backend.ureca.cylin0201.startspring.comment.repository.CommentRepository;
import com.backend.ureca.cylin0201.startspring.domain.Comment;
import com.backend.ureca.cylin0201.startspring.domain.Post;
import com.backend.ureca.cylin0201.startspring.post.dto.PostCommentResponse;
import com.backend.ureca.cylin0201.startspring.post.dto.PostRequest;
import com.backend.ureca.cylin0201.startspring.post.dto.UpdatePostRequest;
import com.backend.ureca.cylin0201.startspring.post.repository.PostRepository;
import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.post.dto.PostResponse;
import com.backend.ureca.cylin0201.startspring.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

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
                post.getMember().getUsername()
        );
    }

    //포스트만 조회
    public PostResponse getPostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Post입니다."));

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getUsername()
        );
    }

    @Transactional(readOnly = true)
    public PostCommentResponse getPostComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findAllByPostId(postId);

        return new PostCommentResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getUsername(),
                comments
        );
    }

    public Page<PostResponse> getAllPosts(Pageable pageable){
        return postRepository.findAllPosts(pageable)
                .map(PostResponse::new);
    }

    //포스트 업데이트
    public PostResponse updatePost(UpdatePostRequest request){
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Post입니다."));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getUsername());
    }

    //포스트 삭제
    public Long deletePost(Long postId){
        postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Post입니다."));

        postRepository.deleteById(postId);
        return postId;
    }

    //검색 기능
    public Page<PostResponse> searchPostsByMemberName(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Page.empty();
        }
        return postRepository.searchIdByMemberName(keyword, pageable)
                .map(PostResponse::new);
    }

    public Page<PostResponse> searchPostsByTitleOrContent(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Page.empty();
        }
        return postRepository.searchIdByTitleOrContent(keyword, pageable)
                .map(PostResponse::new);
    }
}