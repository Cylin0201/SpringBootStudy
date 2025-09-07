package com.backend.ureca.cylin0201.startspring.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdatePostRequest {
    private Long postId;
    private String title;
    private String content;
}
