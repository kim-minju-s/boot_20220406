package com.example.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.MemberDTO;
import com.example.mapper.MemberMapper;

// 로그인에서 버튼을 누르면 서비스로 이메일 전달됨.
@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired MemberMapper mMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("UserDetailsService : "+ username);
		
		MemberDTO member = mMapper.memberEmail(username);
		
        // 권한 정보를 문자열 배열로 만듦.
        String[] strRole = { member.getUrole() };

        // String 배열 권한을 Collection<> 변환함.
        Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(strRole);
		
		//UserDetails -> User
		User user = new User(
				member.getUemail(),
				member.getUpw(),
				roles);
		return user;
	}

}
