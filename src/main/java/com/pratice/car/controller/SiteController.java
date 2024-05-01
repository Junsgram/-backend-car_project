package com.pratice.car.controller;

import com.pratice.car.dto.DealerResDto;
import com.pratice.car.dto.MemberJoinDto;
import com.pratice.car.dto.MemberLoginDto;
import com.pratice.car.dto.TokenDto;
import com.pratice.car.service.DealerRegService;
import com.pratice.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/site")
@CrossOrigin(origins = "http://localhost:3000")
public class SiteController {
    private final MemberService memberService;
    private final DealerRegService dealerRegService;

    //로그인 요청
    @PostMapping("/login")
    public TokenDto login(@RequestBody MemberLoginDto memberLoginDto){
        TokenDto token = memberService.login(memberLoginDto.getUsername()
                ,memberLoginDto.getPassword());
        return token;
    }

    //회원가입요청
    @PostMapping("/join")
    public String memberJoin(@RequestBody MemberJoinDto memberJoinDto){
        try{
            memberService.saveMember(memberJoinDto);
            return "ok";
        }
        catch (Exception e){
            return "fail";
        }
    }

    //딜러등록 요청
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

}
