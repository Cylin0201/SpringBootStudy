package com.backend.ureca.cylin0201.startspring.domain;

import com.backend.ureca.cylin0201.startspring.member.dto.MemberResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    private String role;    //ROLE_USER, ROLE_ADMIN

    // 양방향 관계 (옵션)
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private final List<Post> posts = new ArrayList<>();


    public void addPost(Post post){
        posts.add(post);
        post.setMember(this);
    }

    public MemberResponse from(){
        return new MemberResponse(this.getId(), this.getUsername());
    }

    public static Member createMember(String username, String rawPassword, PasswordEncoder passwordEncoder){
        return Member.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .build();
    }
}
