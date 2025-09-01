package com.backend.ureca.cylin0201.startspring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;
}
