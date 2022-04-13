package com.example.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

// 필요한 항목만 가져오기
public interface BuyProjection {
	
	// 주문번호
	Long getNo();
	
	// 주문수량
	Long getCnt();
	
	// 주문날짜
	Date getRegdate();
	
	// 물품명
	@Value("#{target.item.iname}")
	String getItemIname();
	
	// 물품가격
	@Value("#{target.item.iprice}")
	Long getItemIprice();
	
	// 회원이름
	@Value("#{target.member.uname}")
	String getMemberUname();
	
	// 회원연락처
	@Value("#{target.member.uphone}")
	String getMemberUphone();
}
