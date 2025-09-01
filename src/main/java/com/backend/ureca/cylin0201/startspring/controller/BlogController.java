package com.backend.ureca.cylin0201.startspring.controller;

import com.backend.ureca.cylin0201.startspring.domain.Article;
import com.backend.ureca.cylin0201.startspring.dto.AddArticleReq;
import com.backend.ureca.cylin0201.startspring.dto.ArticleResponse;
import com.backend.ureca.cylin0201.startspring.dto.UpdateArticleRequest;
import com.backend.ureca.cylin0201.startspring.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/articles/new")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleReq req){
        Article article = blogService.saveArticle(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article);
    }

    @GetMapping("/articles/all")
    public ResponseEntity<List<ArticleResponse>> getAllArticle(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> findById(@PathVariable Long id){
        Article article = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        blogService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateById(@PathVariable Long id, @RequestBody UpdateArticleRequest req){
        Article article = blogService.update(id, req);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }
}
