package com.backend.ureca.cylin0201.startspring.repository;

import com.backend.ureca.cylin0201.startspring.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
