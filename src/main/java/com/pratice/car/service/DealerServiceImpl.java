package com.pratice.car.service;

import com.pratice.car.constant.Role;
import com.pratice.car.dto.AdminDealerDto;
import com.pratice.car.entity.Dealer;
import com.pratice.car.entity.DealerRegister;
import com.pratice.car.entity.Member;
import com.pratice.car.repository.DealerRegisterRepository;
import com.pratice.car.repository.DealerRepository;
import com.pratice.car.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService{
    private final DealerRepository dealerRepository;
    private final MemberRepository memberRepository;
    private final DealerRegisterRepository dealerRegisterRepository;

    @Override
    public Dealer getDealer(Long dealerId) {
        return dealerRepository.findById(dealerId).get();
    }

    @Override
    public void addDealer(AdminDealerDto dto) {
        // memberId를 조회하여 member 변수에 저장
        Member member = memberRepository.findById(dto.getMemberid()).get();
        // dto -> entity로 변경 후 dealer 변수에 저장
        Dealer dealer1 = dtoToEntity(dto);
        // Dealer객체에 Member 저장
        dealer1.setMember(member);
        // Dealer테이블에 해당값 저장
        Dealer dealer = dealerRepository.save(dealer1);
        // Member테이블에 비어있는 dealerId 값 할당
        member.setDealerId(dealer.getId());
        // Member테이블 member을 dealer로 변경
        member.setRole(Role.DEALER);
        // dto로 전달받은 MemberId를 조회하여 딜러 등록
        DealerRegister dealerRegister = dealerRegisterRepository.findByMemberId(dto.getMemberid());
        // state를 승인으로 할당
        dealerRegister.setDealerState("승인");
        // 테이블에 해당 값 저장
        dealerRegisterRepository.save(dealerRegister);
        // member에 해당 내용 저장
        memberRepository.save(member);
    }
    }
