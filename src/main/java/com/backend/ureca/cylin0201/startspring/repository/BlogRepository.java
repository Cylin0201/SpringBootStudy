package com.backend.ureca.cylin0201.startspring.repository;

import com.backend.ureca.cylin0201.startspring.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
