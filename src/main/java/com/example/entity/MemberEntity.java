package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Entity
@Data
@Table(name="MEMBER")
public class MemberEntity {

	  // 이메일
	  @Id
	  private String uemail;
	  
	  // 암호
	  @Column(nullable = false)
	  @JsonProperty(access = Access.WRITE_ONLY)
	  private String upw;
	  
	  // 이름
	  private String uname;
	  
	  // 연락처
	  private String uphone;
	  
	  // 권한
	  private String urole;
	  
	  @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss.SSS")
	  @CreationTimestamp	// CURRENT_DATE
	  @Column(name = "UREGDATE")
	  private Date regdate;

	  
	
}
