package com.pratice.car.repository;

import com.pratice.car.entity.DealerRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRegisterRepository extends JpaRepository<DealerRegister, Long> {
    DealerRegister findByMemberId(Long id);
}
