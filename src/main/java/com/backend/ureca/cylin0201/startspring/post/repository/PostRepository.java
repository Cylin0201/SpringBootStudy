package com.backend.ureca.cylin0201.startspring.post.repository;

import com.backend.ureca.cylin0201.startspring.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN p.member m WHERE m.username LIKE %:keyword%")
    List<Post> searchIdByMemberName(@Param("keyword") String keyword);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Post> searchIdByTitleOrContent(@Param("keyword") String keyword);
}