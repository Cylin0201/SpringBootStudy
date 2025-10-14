package com.backend.ureca.cylin0201.startspring.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CommentRequest {
    private String memberName;
    private Long postId;
    private Long parentId;
    private String content;
}
