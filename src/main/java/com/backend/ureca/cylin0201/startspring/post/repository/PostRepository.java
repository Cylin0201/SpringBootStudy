package com.backend.ureca.cylin0201.startspring.post.repository;

import com.backend.ureca.cylin0201.startspring.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p.id FROM Post p WHERE p.title LIKE %:keyword%")
    List<Long> searchIdByTitle(@Param("keyword") String keyword);

    @Query("SELECT p.id FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Long> searchIdByTitleOrContent(@Param("keyword") String keyword);
}