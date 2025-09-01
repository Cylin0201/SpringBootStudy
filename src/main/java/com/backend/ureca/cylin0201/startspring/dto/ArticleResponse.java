package com.backend.ureca.cylin0201.startspring.dto;

import com.backend.ureca.cylin0201.startspring.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {
    private String title;
    private String content;

    public ArticleResponse(Article article){
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
