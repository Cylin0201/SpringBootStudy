package com.backend.ureca.cylin0201.startspring.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "article")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder        //추후에 @AllArgsConstructor를 적용하기
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
