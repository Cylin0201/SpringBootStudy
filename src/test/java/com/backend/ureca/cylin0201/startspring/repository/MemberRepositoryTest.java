package com.backend.ureca.cylin0201.startspring.repository;

import com.backend.ureca.cylin0201.startspring.user.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest {
    private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    Member member = Member.builder()
            .userName("aaa")
            .password("aaa")
            .build();
    @Test
    @DisplayName("Save")
    void save() {
        memberRepository.save(member);
        assertThat(memberRepository.getLength()).isEqualTo(1);

    }

//    @Test
//    void findById() {
//    }
}