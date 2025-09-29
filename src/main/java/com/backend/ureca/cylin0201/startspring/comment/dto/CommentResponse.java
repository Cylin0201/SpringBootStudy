package com.backend.ureca.cylin0201.startspring.comment.dto;

import com.backend.ureca.cylin0201.startspring.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final String authorName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final Long parentId; // 부모 댓글 id
    private static List<CommentResponse> children; // 대댓글 리스트

    @Builder
    public CommentResponse(Long id, String content, String authorName,
                           LocalDateTime createdAt, LocalDateTime updatedAt,
                           Long parentId, List<CommentResponse> children) {
        this.id = id;
        this.content = content;
        this.authorName = authorName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.parentId = parentId;
        CommentResponse.children = children;
    }

    public static CommentResponse from(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorName(comment.getMember().getUsername())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .children(children)
                .build();
    }
}
