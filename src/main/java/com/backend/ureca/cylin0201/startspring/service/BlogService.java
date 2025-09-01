package com.backend.ureca.cylin0201.startspring.service;

import com.backend.ureca.cylin0201.startspring.domain.Article;
import com.backend.ureca.cylin0201.startspring.dto.AddArticleReq;
import com.backend.ureca.cylin0201.startspring.dto.UpdateArticleRequest;
import com.backend.ureca.cylin0201.startspring.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public Article saveArticle(AddArticleReq addArticleReq){
        return blogRepository.save(addArticleReq.toEntity());
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found : " + id ));
        return article;
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest req){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found : " + id));
        article.update(req.getTitle(), req.getContent());
        return article;
    }
}

