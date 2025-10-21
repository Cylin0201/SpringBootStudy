package com.backend.ureca.cylin0201.startspring.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MyPageDto {
    private String memberName;
    
    //작성한 포스트의 수
    private Long postCnt;
    //작성한 댓글의 수
    private Long commentCnt;
}
