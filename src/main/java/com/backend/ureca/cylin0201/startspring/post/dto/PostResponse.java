package com.backend.ureca.cylin0201.startspring.post.dto;
import com.backend.ureca.cylin0201.startspring.domain.Post;
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

    public PostResponse (Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.memberName = post.getMember().getUsername();
    }
}
