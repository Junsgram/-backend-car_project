package com.pratice.car.service;

import com.pratice.car.dto.DealerRegListDto;
import com.pratice.car.dto.DealerResDto;
import com.pratice.car.entity.DealerRegister;

import java.util.List;

public interface DealerRegService {
    // 딜러 신청 등록하기
    void dealerRegSave(DealerResDto resDto);
    // 딜러 목록 조회하기
    List<DealerRegListDto> getDealerRegList();

    // dto -> entity 변환
    default DealerRegister dtoToEntity(DealerResDto resDto) {
        DealerRegister entity = DealerRegister.builder()
                .name(resDto.getName())
                .phone(resDto.getPhone())
                .location(resDto.getLocation())
                .message(resDto.getMessage())
                .memberId(resDto.getMemberId())
                .build();
        return entity;
    }

    // entity -> dto
    default DealerRegListDto entityToDto(DealerRegister dealerRegister){
        DealerRegListDto dto = DealerRegListDto.builder()
                .id(dealerRegister.getId())
                .name(dealerRegister.getName())
                .phone(dealerRegister.getPhone())
                .location(dealerRegister.getLocation())
                .message(dealerRegister.getMessage())
                .memberId(dealerRegister.getMemberId())
                .dealerState(dealerRegister.getDealerState())
                .build();
        return dto;
    }
}
