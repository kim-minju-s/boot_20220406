package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MemberDTO;
import com.example.mapper.MemberMapper;

// backend만 구현함. 화면구현X, vue.js 또는 react.js 연동

@RestController
@RequestMapping("/api/rest_customer")
public class CustomerRestController {

	@Autowired MemberMapper mMapper;
	
	// /ROOT/rest_customer/join
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
