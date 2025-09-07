package com.backend.ureca.cylin0201.startspring.post.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String memberName;

}
