package com.backend.ureca.cylin0201.startspring.member.dto;

import com.backend.ureca.cylin0201.startspring.domain.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginDto{
    public String userName;
    public String password;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .userName(userName)
                .password(password)
                .build();
    }
}
