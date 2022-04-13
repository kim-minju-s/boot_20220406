package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;

@Entity
@Data
@Table(name= "BUY")
@SequenceGenerator(name="SEQ_BUY",
	sequenceName = "SEQ_BUY_NO", 
	allocationSize = 1, 
	initialValue = 1)
public class BuyEntity {
	
	@Id	// 기본키
	@Column(name="BNO")	// 컬럼명
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "SEQ_BUY")
	private Long no;
	
	// 주문수량
	@Column(name = "BCNT")
	private Long cnt;
	
	// 주문일
	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss.SSS")
	@CreationTimestamp	// CURRENT_DATE
	@Column(name = "BREGDATE")
	private Date regdate;
	
	
	// Entity를 사용할 경우 return 할 때 물품과 회원정보 전부를 불러옴
	// Projection 인터페이스에 필요한 정보만 가공해서 사용해야됨
	
	// 물품 테이블(외래키)
	@ManyToOne
	@JoinColumn(name = "ICODE")
	private ItemEntity item;
	
	// 회원 테이블(외래키)
	@ManyToOne
	@JoinColumn(name = "UEMAIL")
	private MemberEntity member;
	
}
