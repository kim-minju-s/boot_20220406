package com.example.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// 토큰을 발행 및 정보추출용
@Service
public class JwtUtil {

	private final String SECURITY_KEY = "fjehje#$4343";
	
	// 1000 => 1초
	private final long VALIDATETIME = 1000*60*60*9;	//9H
	
	// 토큰 생성(아이디 정보)
	public String generatorToken(String username) {
		
		Map<String, Object> map = new HashMap<>();
		
		String token = Jwts.builder()
				.setClaims(map)
				.setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + VALIDATETIME))
				.signWith(SignatureAlgorithm.HS256, SECURITY_KEY)
				.compact();
		
		return token;
	}
	
	// 정보 추출용 메소드
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
    
    // 토큰에서 아이디 추출
    public String extractUsername(String token) {
    	return extractClaim(token, Claims::getSubject);
    }
    
    // 토큰에서 만료시간 추출
    public Date extractExpiration(String token) {
    	return extractClaim(token, Claims::getExpiration);
    }
    
    // 유효시간 체크
    public boolean isTokenExpired(String token) {
    	return extractExpiration(token).before(new Date());
    }
    
    // 토큰이 유효한지 체크
    public boolean isTokenValidation(String token, String uid) {
    	String username = extractUsername(token);
    	if (username.equals(uid) && isTokenExpired(token)) {
			return true;
		}
    	return false;
    }
}
