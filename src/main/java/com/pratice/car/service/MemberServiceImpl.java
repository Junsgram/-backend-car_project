package com.pratice.car.service;

import com.pratice.car.dto.MemberDto;
import com.pratice.car.dto.MemberJoinDto;
import com.pratice.car.dto.TokenDto;
import com.pratice.car.entity.Member;
import com.pratice.car.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Override
    public Member saveMember(MemberJoinDto dto) {
        Member member = dtoToEntity(dto);
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
        return memberRepository.save(member);
    }

    // 이메일 체크
    @Override
    public String validateDuplicateMember(MemberJoinDto dto) {
        Member findMember = memberRepository.findByEmail(dto.getEmail());
        // 이메일 존재 여부 확인
        if (findMember != null) {
            return "fail";
        }
        return "ok";
    }

    // 로그인

    /**
     * 로그인 요청 시 전달된 memberId(email)과 password를 기반으로 Authentication 객체를 생성
     * authenticate() 메소드를 통해 요청된 Member에 대한 검증을 진행 -> loadUserByUsername메소드가 실행된다.
     * 검증에 성공하면 Authentication 객체를 기반으로 JWT 토큰을 생성
     */
    @Override
    public TokenDto login(String memberId, String password) {
        // 1. 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(memberId, password);
        // 2. 검증 authentication 메소드가 실행될 때 요청된 Member에 대한 검증을 진행
        // CustomUserDetailsService에서 구현한 loadUser
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
        System.out.println("==========" + authentication);
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        Member member = memberRepository.findByEmail(memberId);
        tokenDto.setDealerId(member.getDealerId());
        tokenDto.setRole(member.getRole());
        tokenDto.setName(member.getName());
        tokenDto.setMemberId(member.getId());
        return tokenDto;
    }
    // 회원목록 리스트 출력

    @Override
    public List<MemberDto> getMemberList() {
        List<Member> result = memberRepository.findAll();
        List<MemberDto> memberList
                = result.stream().map(en -> entityToDto(en)).collect(Collectors.toList());
        return memberList;
    }
}
