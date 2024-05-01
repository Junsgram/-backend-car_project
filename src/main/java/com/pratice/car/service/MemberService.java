package com.pratice.car.service;

import com.pratice.car.constant.Role;
import com.pratice.car.dto.MemberDto;
import com.pratice.car.dto.MemberJoinDto;
import com.pratice.car.dto.TokenDto;
import com.pratice.car.entity.Member;

import java.util.List;

public interface MemberService {
    // 회원가입
    Member saveMember(MemberJoinDto dto);
    // 이메일 체크
    String validateDuplicateMember(MemberJoinDto dto);
    // 로그인
    TokenDto login(String memberId, String password);
    // 회원목록 받아오기
    List<MemberDto> getMemberList();

    // dto -> entity 전환
    default Member dtoToEntity(MemberJoinDto dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .address(dto.getAddress())
                .role(Role.USER)
                .build();
        return member;
    }

    // entitiy -> dto
    default MemberDto entityToDto(Member member) {
        MemberDto dto = MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .dealerId(member.getDealerId())
                .role(member.getRole())
                .build();
        return dto;
    }
}
