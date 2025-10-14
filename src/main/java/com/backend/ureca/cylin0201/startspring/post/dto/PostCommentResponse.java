package com.backend.ureca.cylin0201.startspring.post.dto;

import com.backend.ureca.cylin0201.startspring.domain.Comment;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class PostCommentResponse {
    private Long id;
    private String title;
    private String content;
    private String memberName;
    private List<Comment> comments;
}
