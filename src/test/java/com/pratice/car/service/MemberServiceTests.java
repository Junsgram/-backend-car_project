package com.pratice.car.service;

import com.pratice.car.dto.MemberJoinDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService memberService;

    @DisplayName("Member insert 테스트")
    @Test
    void insertMember(){
        MemberJoinDto dto = MemberJoinDto.builder()
                .name("yun")
                .email("jun@mail.com")
                .password("1234")
                .address("울산시 남구 달동")
                .build();
        memberService.saveMember(dto);
    }
}
