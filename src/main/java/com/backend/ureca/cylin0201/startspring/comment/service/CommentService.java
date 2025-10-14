package com.backend.ureca.cylin0201.startspring.comment.service;

import com.backend.ureca.cylin0201.startspring.comment.dto.CommentRequest;
import com.backend.ureca.cylin0201.startspring.comment.dto.CommentResponse;
import com.backend.ureca.cylin0201.startspring.comment.repository.CommentRepository;
import com.backend.ureca.cylin0201.startspring.domain.Comment;
import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.domain.Post;
import com.backend.ureca.cylin0201.startspring.member.repository.MemberRepository;
import com.backend.ureca.cylin0201.startspring.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 댓글 엔티티화
    public Comment createComment(CommentRequest req) {
        if (req.getMemberName() == null) {
            throw new IllegalArgumentException("멤버 아이디가 null입니다.");
        }
        if (req.getPostId() == null) {
            throw new IllegalArgumentException("포스트 아이디가 null입니다.");
        }

        Member member = memberRepository.findByUsername(req.getMemberName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버 ID"));
        Post post = postRepository.findById(req.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 ID"));

        Comment parent = null;
        if (req.getParentId() != null) {
            parent = commentRepository.findById(req.getParentId())
                    .orElse(null);
        }

        return Comment.builder()
                .member(member)
                .post(post)
                .parent(parent)
                .content(req.getContent())
                .build();
    }

    //댓글 포스트 + 저장
    public void postComment(CommentRequest req){
        Comment comment = createComment(req);
        if (comment.getParent() != null){
            comment.getParent().addChild(comment);
        }

        commentRepository.save(comment);
    }

    //댓글 삭제
    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        commentRepository.deleteById(commentId);
    }

    //댓글 수정
    public void updateComment(Long commentId, String content){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.setContent(content);
    }

    public CommentResponse getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
            return CommentResponse.from(comment);
    }
    
    //모든 댓글 조회
    public List<CommentResponse> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream()
                .map(CommentResponse::from)
                .toList();
    }
}
