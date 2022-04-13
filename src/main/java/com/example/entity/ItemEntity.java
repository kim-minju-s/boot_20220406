package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
@Table(name="ITEM")
@SequenceGenerator(name="SEQ_ITEM",
	sequenceName = "SEQ_ITEM_ICODE", 
	allocationSize = 1, 
	initialValue = 1)
public class ItemEntity {
	
	  // 물품코드
	  @Id
	  @GeneratedValue(strategy = GenerationType.SEQUENCE,
	  	generator = "SEQ_ITEM")
	  private Long icode;
	  
	  // 물품이름
	  private String iname;
	  
	  // 물품내용
	  @Lob
	  private String icontent;
	  
	  // 물품가격
	  private Long iprice;
	  
	  // 물품수량
	  private Long iquantity;
	  
	  @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss.SSS")
	  @CreationTimestamp	// CURRENT_DATE
	  @Column(name = "IREGDATE")
	  private Date regdate;
	  
	  // 이미지
	  @Lob
	  private String iimage;
	  
	  // 이미지사이즈
	  private Long iimagesize;
	  
	  // 이미지타입
	  private String iimagetype;
	  
	  // 이미지명
	  private String iimagename;
	  
	  // 회원 테이블(판매자)
	  @JsonBackReference
	  @ManyToOne
	  @JoinColumn(name = "uemail")
	  private MemberEntity member;

}
