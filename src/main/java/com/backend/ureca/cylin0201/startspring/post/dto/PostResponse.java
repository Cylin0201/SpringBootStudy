package com.backend.ureca.cylin0201.startspring.post.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String memberName;

}
