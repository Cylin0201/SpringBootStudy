package com.backend.ureca.cylin0201.startspring.post.repository;

import com.backend.ureca.cylin0201.startspring.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 전체 게시글 조회
    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    Page<Post> findAllPosts(Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.member m WHERE m.username LIKE %:keyword%")
    Page<Post> searchIdByMemberName(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    Page<Post> searchIdByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);
}