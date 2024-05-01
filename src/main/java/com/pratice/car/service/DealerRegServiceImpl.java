package com.pratice.car.service;

import com.pratice.car.dto.DealerRegListDto;
import com.pratice.car.dto.DealerResDto;
import com.pratice.car.entity.DealerRegister;
import com.pratice.car.repository.DealerRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealerRegServiceImpl implements DealerRegService {
    private final DealerRegisterRepository dealerRegisterRepository;

    // 딜러 신청 게시글 등록
    @Override
    public void dealerRegSave(DealerResDto resDto) {
        DealerRegister dealerRegister = dtoToEntity(resDto);
        dealerRegister.setDealerState("신청");
        dealerRegisterRepository.save(dealerRegister);
    }
    // 딜러 목록 조회하기

    @Override
    public List<DealerRegListDto> getDealerRegList() {
        List<DealerRegister> result = dealerRegisterRepository.findAll();
        List<DealerRegListDto> dtos =
                result.stream().map(en->entityToDto(en)).collect(Collectors.toList());
        return dtos;
    }
}
