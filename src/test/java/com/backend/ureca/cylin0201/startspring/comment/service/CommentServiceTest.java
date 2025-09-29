package com.backend.ureca.cylin0201.startspring.comment.service;

import com.backend.ureca.cylin0201.startspring.comment.dto.CommentRequest;
import com.backend.ureca.cylin0201.startspring.comment.dto.CommentResponse;
import com.backend.ureca.cylin0201.startspring.comment.repository.CommentRepository;
import com.backend.ureca.cylin0201.startspring.domain.Comment;
import com.backend.ureca.cylin0201.startspring.domain.Member;
import com.backend.ureca.cylin0201.startspring.domain.Post;
import com.backend.ureca.cylin0201.startspring.member.repository.MemberRepository;
import com.backend.ureca.cylin0201.startspring.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private Member testMember;
    private Post testPost;

    @BeforeEach
    void setUp() {
        // 테스트용 Member, Post 생성
        testMember = Member.builder()
                .username("testUser")
                .password("password")
                .build();
        memberRepository.save(testMember);

        testPost = Post.builder()
                .title("Test Post")
                .content("This is a test post")
                .member(testMember)
                .build();
        postRepository.save(testPost);
    }

    @Test
    void testPostCommentWithoutParent() {
        CommentRequest request = new CommentRequest();
        request.setMemberId(testMember.getId());
        request.setPostId(testPost.getId());
        request.setContent("일반 댓글");

        commentService.postComment(request);

        List<Comment> comments = commentRepository.findAllByPostId(testPost.getId());
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getParent()).isNull(); // parentId가 null이면 parent도 null
        assertThat(comments.get(0).getContent()).isEqualTo("일반 댓글");
    }

    @Test
    void testPostCommentWithParent() {
        // 먼저 부모 댓글 생성
        CommentRequest parentRequest = new CommentRequest();
        parentRequest.setMemberId(testMember.getId());
        parentRequest.setPostId(testPost.getId());
        parentRequest.setContent("부모 댓글");
        commentService.postComment(parentRequest);

        Comment parent = commentRepository.findAllByPostId(testPost.getId()).get(0);

        // 자식 댓글 생성
        CommentRequest childRequest = new CommentRequest();
        childRequest.setMemberId(testMember.getId());
        childRequest.setPostId(testPost.getId());
        childRequest.setParentId(parent.getId());
        childRequest.setContent("대댓글");

        commentService.postComment(childRequest);

        List<Comment> comments = commentRepository.findAllByPostId(testPost.getId());
        assertThat(comments).hasSize(2);

        Comment child = comments.stream()
                .filter(c -> c.getParent() != null)
                .findFirst()
                .orElseThrow();

        assertThat(child.getParent().getId()).isEqualTo(parent.getId());
        assertThat(parent.getChildren()).contains(child);
    }

    @Test
    void testUpdateComment() {
        CommentRequest request = new CommentRequest();
        request.setMemberId(testMember.getId());
        request.setPostId(testPost.getId());
        request.setContent("초기 내용");
        commentService.postComment(request);

        Comment saved = commentRepository.findAllByPostId(testPost.getId()).get(0);
        commentService.updateComment(saved.getId(), "수정된 내용");

        Comment updated = commentRepository.findById(saved.getId()).get();
        assertThat(updated.getContent()).isEqualTo("수정된 내용");
    }

    @Test
    void testDeleteComment() {
        CommentRequest request = new CommentRequest();
        request.setMemberId(testMember.getId());
        request.setPostId(testPost.getId());
        request.setContent("삭제 테스트");
        commentService.postComment(request);

        Comment saved = commentRepository.findAllByPostId(testPost.getId()).get(0);
        commentService.deleteComment(saved.getId());

        assertThat(commentRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void testGetComment() {
        CommentRequest request = new CommentRequest();
        request.setMemberId(testMember.getId());
        request.setPostId(testPost.getId());
        request.setContent("조회 테스트");
        commentService.postComment(request);

        Comment saved = commentRepository.findAllByPostId(testPost.getId()).get(0);
        CommentResponse response = commentService.getComment(saved.getId());

        assertThat(response.getContent()).isEqualTo("조회 테스트");
        assertThat(response.getAuthorName()).isEqualTo(testMember.getUsername());
        assertThat(response.getParentId()).isNull();
    }

    @Test
    void testGetCommentsByPost() {
        CommentRequest req1 = new CommentRequest();
        req1.setMemberId(testMember.getId());
        req1.setPostId(testPost.getId());
        req1.setContent("댓글1");
        commentService.postComment(req1);

        CommentRequest req2 = new CommentRequest();
        req2.setMemberId(testMember.getId());
        req2.setPostId(testPost.getId());
        req2.setContent("댓글2");
        commentService.postComment(req2);

        List<CommentResponse> responses = commentService.getCommentsByPost(testPost.getId());
        assertThat(responses).hasSize(2);
        assertThat(responses).extracting("content")
                .containsExactlyInAnyOrder("댓글1", "댓글2");
    }

    @Test
    void testCreateCommentWithInvalidMember() {
        CommentRequest request = new CommentRequest();
        request.setMemberId(999L); // 존재하지 않는 멤버 ID
        request.setPostId(testPost.getId());
        request.setContent("테스트");

        assertThrows(IllegalArgumentException.class, () -> commentService.createComment(request));
    }
}