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
import lombok.ToString;

@Entity
@Data
@Table(name = "BOARD10_REPLY")	// 테이블명
// 생성할 시퀀스
@SequenceGenerator(name="SEQ_BOARD_REPLY",
	sequenceName = "SEQ_BOARD10_REPLY_RNO", 
	allocationSize = 1, 
	initialValue = 1)
public class BoardReplyEntity {

	@Id
	@Column(name = "RNO")	// 기본키, 컬럼명 RNO
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
		generator = "SEQ_BOARD_REPLY")
	private long no;
	
	
	@Column(name = "RCONTENT", length=300)	// VARCHAR2(300)
	private String content;
	
	@Column(name = "RWRITER", length = 50)
	private String writer;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss.SSS")
	@CreationTimestamp	// CURRENT_DATE
	@Column(name = "RREGDATE")
	private Date regdate;
	
	@ToString.Exclude
	@ManyToOne	// 외래키, 외래키에 해당하는 기본키값만 가져옴(BIGINT)
	@JoinColumn(name = "BOARD")
	private BoardEntity board;
	
}
