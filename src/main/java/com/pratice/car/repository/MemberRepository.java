package com.pratice.car.repository;

import com.pratice.car.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일로 조회
    Member findByEmail(String email);
}
