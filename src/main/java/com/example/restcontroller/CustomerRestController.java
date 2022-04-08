package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MemberDTO;
import com.example.jwt.JwtUtil;
import com.example.mapper.MemberMapper;
import com.example.service.UserDetailServiceImpl;

// backend만 구현함. 화면구현X, vue.js 또는 react.js 연동

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

	@Autowired MemberMapper mMapper;
	
	@Autowired JwtUtil jwtUtil;
	
	@Autowired UserDetailServiceImpl userDetailsService;
	
	
	// 암호변경 (토큰, 현재암호, 변경암호)
	// 127.0.0.1:9090/ROOT/api/customer/updatepassword
	@RequestMapping(value="/updatepassword",
			method = {RequestMethod.PUT},
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> customerUpdatePwPUT(
			@RequestHeader(name="TOKEN") String token,
			@RequestBody MemberDTO member){
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		try {
			String username = jwtUtil.extractUsername(token);
			System.out.println("토큰에서 아이디 : " + username);

			// Security 서비스
			UserDetails user = userDetailsService.loadUserByUsername(username);
			
			BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			
			// 암호화 되지 않은것과 암호화 된 것을 비교
			if ( bcpe.matches(member.getUpw(), user.getPassword())) {
				mMapper.memberUpdatePw(username, bcpe.encode(member.getUpw1()));
				map.put("status", 200);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	// 회원정보 수정 (토큰, 이름, 전화번호)
	// 127.0.0.1:9090/ROOT/api/customer/updatemember
	@RequestMapping(value="/updatemember",
			method = {RequestMethod.PUT},
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> customerUpdatePUT(
			@RequestHeader(name="TOKEN") String token,
			@RequestBody MemberDTO member){
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		try {
			String username = jwtUtil.extractUsername(token);

			// Security 서비스
			UserDetails user = userDetailsService.loadUserByUsername(username);
			
			BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			
			// 암호화 되지 않은것과 암호화 된 것을 비교
			if ( bcpe.matches(member.getUpw(), user.getPassword())) {
				
				mMapper.updateMember(username, member.getUname(), member.getUphone());
				map.put("status", 200);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	// 회원탈퇴 update 중요정보 내용만 지우기 (토큰, 현재암호, 아이디를 제외한 내용 지우기)
	// 127.0.0.1:9090/ROOT/api/customer/deletemember
	@RequestMapping(value="/deletemember",
			method = {RequestMethod.PUT},
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> customerDeletePUT(
			@RequestHeader(name="TOKEN") String token,
			@RequestBody MemberDTO member){
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		try {
			String username = jwtUtil.extractUsername(token);

			// Security 서비스
			UserDetails user = userDetailsService.loadUserByUsername(username);
			
			BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			
			// 암호화 되지 않은것과 암호화 된 것을 비교
			if ( bcpe.matches(member.getUpw(), user.getPassword())) {
				
				mMapper.deleteMember(username);
				map.put("status", 200);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	
	// 마이페이지
	// 127.0.0.1:9090/ROOT/api/customer/mypage
	@RequestMapping(value="/mypage",
			method = {RequestMethod.GET},
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> customerMypageGET(
			@RequestHeader(name="TOKEN") String token){
		
		System.out.println("마이페이지에서 token : " + token);
		
		String username = jwtUtil.extractUsername(token);
		System.out.println("마이페이지 아이디: " + username);
		
		// 토큰이 있어야 실행됨.
		Map<String, Object> map = new HashMap<>();
		map.put("status", 200);

		return map;
	}
	
	
	// 로그인
	// 127.0.0.1:9090/ROOT/api/customer/login
	@RequestMapping(value="/login",
			method = {RequestMethod.POST},
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> customerLoginPOST(
			@RequestBody MemberDTO member){
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);	// 정삭적이지 않을 때
		
		// Security 서비스
		UserDetails user = userDetailsService.loadUserByUsername(member.getUemail());
		
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		// 암호화 되지 않은것과 암호화 된 것을 비교
		if ( bcpe.matches(member.getUpw(), user.getPassword())) {
			String token = jwtUtil.generatorToken(member.getUemail());
			map.put("status", 200);	// 0 -> 200
			map.put("token", token);
		}

		return map;
	}
	
	
	// 회원가입(고객만)
	// /ROOT/api/customer/join
	@RequestMapping(value="/join", 
			method = {RequestMethod.POST},	// POST로 받음
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> customerJoinPOST(
			@RequestBody MemberDTO member){	//@ModelAttribute 대신 사용
		
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		member.setUpw(bcpe.encode(member.getUpw()));
		member.setUrole("CUSTOMER");
		
		int ret = mMapper.memberJoin(member);
		Map<String, Object> map = new HashMap<>();
		
		map.put("status", 0);
		if (ret == 1) {
			map.put("status", 200);
		}
		return map;
	}
}
