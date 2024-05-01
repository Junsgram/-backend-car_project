package com.pratice.car.service;

import com.pratice.car.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtTokenProvider {
    private final Key key;

    // 생성자 secret Key를 디코딩하여 JWT 서명에 사용 예정
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        // 주입된 secretKey를 Base64 디코딩 하여 byte배열로 리턴
        byte[] keybytes = Decoders.BASE64.decode(secretKey);
        // 변환된 바이트 배열을 사용해서 Hmacshakey를 생성 -> JWT 서명을 생성하고 검증
        this.key = Keys.hmacShaKeyFor(keybytes);
    }

    // 유저 정보를 가지고 있는 AccessToken, RefreshToken을 생성하는 메소드
    public TokenDto generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                // 객체에서 권한을 추출해서 문자열로 추출
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        long now =(new Date()).getTime(); // 현재 데이트 객체를 밀리초로 리턴
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now+86400000); // 현재 시간에 하루를 저장 밀리초 84000000
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // 로그인한 사용자의 이메일
                .claim("roll", authorities) // 사용자에 권한 정보를 추가해준다. ->USER, ADMIN, DEALER 중 1개
                .setExpiration(accessTokenExpiresIn) // JWT만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 서명을 추가 진행 JWT의 무결성을 보장, 위조 방지용 HS256알고리즘을 사용하여 비밀키를 이용하여 서명을 생성
                .compact(); // 문자열로 반환하는 메소드
        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 864000000))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
        return TokenDto.builder()
                // 토큰의 타입을 나타내는 속성 Bearer 토큰을 사용
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 복호화하여 토큰에 들어있는 정보를 꺼내느 메소드
    // Authentication 스프링 시큐리티 인증된 정보를 담고 있는 인터페이스
    // accessToken을 전달받아 Authentication을 리턴
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if(claims.get("roll") == null ) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities =
                // "USER, ADMIN" --> {"USER", "ADMIN"}
                Arrays.stream(claims.get("roll").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(),"",authorities);
        // 이메일 , 패스워드 , 권한
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메소드
    public boolean validateToken(String token) {
        // Jwts.parserBuilder() : JWT 파싱하는 빌더를 생성
        // setSigningKey(key) : 서명 키를 설정 JWT를 검증할 때 사용
        // build() Jwtparser 객체를 빌드함
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e ) {
            log.info("유효하지 않는 JWT 웹 토큰입니다.",e);
        }catch(ExpiredJwtException e )   {
            log.info("만료된 토큰입니다.", e);
        }catch(UnsupportedJwtException e) {
            log.info("입증되지 않는 JWT 토큰입니다.", e);
        }catch(IllegalArgumentException e) {
            log.info("JWT claims가 비어있습니다.",e);
        }
        return false;
    }

    // 주어진 accessToken을 해독하고 클레임 객체를 리턴함
    private Claims parseClaims(String accessToken) {
        //Jwts.parserBuilder() -> Jwt(Json web Token)을 파싱하는 필더를 생성
        // setSigningKey(key) -> 서명키를 설정 JWT검증 시 사용
        // parseClaimsJws(accessToken) 엑세스 토큰을 파싱해서 서명을 확인
        // .getBody() JWT본문을 얻는다. Clalims(jwt의 정보 포함하고있다)를 반환
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(accessToken).getBody();
    }
}
