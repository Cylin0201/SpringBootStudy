package com.backend.ureca.cylin0201.startspring.member.dto;

import com.backend.ureca.cylin0201.startspring.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LoginDto{
    private String username;
    private String password;

}
