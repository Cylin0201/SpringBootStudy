package com.backend.ureca.cylin0201.startspring.comment.repository;

import com.backend.ureca.cylin0201.startspring.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.member.id = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);
}
