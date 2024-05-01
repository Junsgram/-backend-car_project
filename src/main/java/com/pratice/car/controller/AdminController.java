package com.pratice.car.controller;

import com.pratice.car.dto.AdminDealerDto;
import com.pratice.car.dto.DealerRegListDto;
import com.pratice.car.dto.MemberDto;
import com.pratice.car.service.DealerRegService;
import com.pratice.car.service.CarService;
import com.pratice.car.service.DealerService;
import com.pratice.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    private final CarService carService;
    private final MemberService memberService;
    private final DealerRegService dealerRegService;
    private final DealerService dealerService;

    @GetMapping("/memberList")
    // 회원목록
    public ResponseEntity getMemberList() {
        List<MemberDto> result  = memberService.getMemberList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // 딜러 요청 목록
    @GetMapping("/dealerRegList")
    public ResponseEntity getDealerRegList() {
        List<DealerRegListDto> result = dealerRegService.getDealerRegList();
        for(DealerRegListDto dto : result) {
            System.out.println(dto.toString());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 딜러 등록하기
    @PostMapping("dealerAdd")
    public ResponseEntity dealerAdd(@RequestBody AdminDealerDto dto) {
        System.out.println(dto.toString());
        dealerService.addDealer(dto);
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }
}
