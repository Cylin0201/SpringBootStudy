package com.backend.ureca.cylin0201.startspring.comment.repository;

import com.backend.ureca.cylin0201.startspring.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}
