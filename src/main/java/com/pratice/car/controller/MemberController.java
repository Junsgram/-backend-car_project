package com.pratice.car.controller;

import com.pratice.car.dto.DealerResDto;
import com.pratice.car.service.DealerRegService;
import com.pratice.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
// react와 연동되어 사용될 예정
@CrossOrigin(origins="http://localhost:3000")
public class MemberController {
    private final MemberService memberService;
    private final DealerRegService dealerRegService;

    //딜러등록하기
    @PostMapping("/register")
    public String memberJoin(@RequestBody DealerResDto dto){
        try{
            dealerRegService.dealerRegSave(dto);
            return "ok";
        }
        catch (Exception e){
            return "fail";
        }
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

    @PostMapping("/usertest")
    public String usertest() {
        // 시큐리티 컨텍스트 홀더 -> 시큐리티 컨텍스트 -> 권한의 값을 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName() == null ) {
            throw new RuntimeException("인증 정보가 없습니다.");
        }
        // getName은 이메일을 Return한다.
        return authentication.getName();
    }
}


