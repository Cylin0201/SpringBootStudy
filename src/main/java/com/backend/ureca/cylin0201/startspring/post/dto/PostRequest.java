package com.backend.ureca.cylin0201.startspring.post.dto;

import com.backend.ureca.cylin0201.startspring.post.domain.Post;
import com.backend.ureca.cylin0201.startspring.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequest {
    private Long memberId;
    private String title;
    private String content;

}
