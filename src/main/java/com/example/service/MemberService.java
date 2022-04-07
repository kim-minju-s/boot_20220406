package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dto.MemberDTO;

@Service
public interface MemberService {

	// 회원가입
	public int memberInsertOne(MemberDTO member);
	
	// 회원탈퇴
	public int memberDeleteOne(String memail);
}
