package com.pratice.car.service;

import com.pratice.car.dto.AdminDealerDto;
import com.pratice.car.entity.Dealer;

public interface DealerService {
    // 딜러 조회
    Dealer getDealer(Long dealerId);

    // 딜러 등록
    void addDealer(AdminDealerDto dto);

    // dto->Entity
    default Dealer dtoToEntity(AdminDealerDto dto) {
        Dealer dealer = Dealer.builder()
                .location(dto.getLocation())
                .name(dto.getName())
                .phone(dto.getPhone())
                .build();
        return dealer;
    }
}
