package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.MemberDTO;
import com.example.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired MemberMapper mMapper;
	
	@Override
	public int memberInsertOne(MemberDTO member) {
		return mMapper.memberJoin(member);
	}

	@Override
	public int memberDeleteOne(String memail) {
		return 0;
	}

}
