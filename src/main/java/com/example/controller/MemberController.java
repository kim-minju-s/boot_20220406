package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.MemberDTO;
import com.example.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {

	@Autowired MemberService mService;
	
	// 회원가입 화면
	@GetMapping(value="/join")
	public String joinGET() {
		return "/member/join";
	}
	
	// 127.0.0.1:9090/ROOT/
	@PostMapping(value="joinaction")
	public String joinPOST(@ModelAttribute MemberDTO member) {
		
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		member.setUpw(bcpe.encode(member.getUpw()));
		member.setUrole("SELLER");
		
		mService.memberInsertOne(member);
		
		return "redirect:/";
	}
	
	// 로그인 화면
	@GetMapping(value="/login")
	public String loginGET() {
		return "/member/login";
	}
}
