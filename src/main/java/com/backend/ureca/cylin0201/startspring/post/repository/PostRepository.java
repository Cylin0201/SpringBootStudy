package com.backend.ureca.cylin0201.startspring.post.repository;

import com.backend.ureca.cylin0201.startspring.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
