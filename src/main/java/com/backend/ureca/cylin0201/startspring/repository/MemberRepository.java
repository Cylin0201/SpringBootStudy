package com.backend.ureca.cylin0201.startspring.repository;

import com.backend.ureca.cylin0201.startspring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
