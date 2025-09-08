package com.backend.ureca.cylin0201.startspring.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PostRequest {
    private Long memberId;
    private String title;
    private String content;
}
