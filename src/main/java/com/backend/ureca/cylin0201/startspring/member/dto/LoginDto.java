package com.backend.ureca.cylin0201.startspring.member.dto;

import com.backend.ureca.cylin0201.startspring.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDto{
    private String username;
    private String password;

}
