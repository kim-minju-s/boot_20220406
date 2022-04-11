package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name="BOARD10")	// 테이블명

// 생성할 시퀀스
@SequenceGenerator(name="SEQ_BOARD",
		sequenceName = "SEQ_BOARD10_BNO", 
		allocationSize = 1, 
		initialValue = 1)
public class BoardEntity {

	@Id	// 기본키
	@Column(name="BNO")	// 컬럼명
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "SEQ_BOARD")	// 시퀀스 적용
	private Long no;	// 타입 변수
	
	@Column(name = "BTITLE", length = 200)	// VARCHAR2(200)
	private String title;
	
	@Lob
	@Column(name = "BCONTENT")
	private String content;
	
	@Column(name = "BWRITER", length = 100)
	private String writer;
	
	@Column(name = "BHIT")
	private Long hit = 1L;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss.SSS")
	@CreationTimestamp	// CURRENT_DATE
	@Column(name = "BREGDATE")
	private Date regdate;
}
