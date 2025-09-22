package com.backend.ureca.cylin0201.startspring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    // 양방향 관계 (옵션)
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private final List<Post> posts = new ArrayList<>();


    //추후에 사용자가 단 댓글 -> 댓글이 달려져 있는 글 조회 가능하게...


    public void addPost(Post post){
        posts.add(post);
        post.setMember(this);
    }
}
