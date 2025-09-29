package com.backend.ureca.cylin0201.startspring.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequest {
    private Long memberId;
    private Long postId;
    private Long parentId;
    private String content;
}
