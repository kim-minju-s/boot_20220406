package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Immutable	// view인 경우 추가, 읽기만 가능 findBy..만 가능
@Entity
@Table(name = "PRODUCT_VIEW")
public class ProductViewEntity {

	@Id
	@Column(name = "no")
	Long no;
	
	@Column(length = 250)
	String name;
	
	Long price;
	
	Long cnt;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss.SSS")
	@CreationTimestamp	// 데이터가 변경될 때도 시간이 바뀜
	private Date uptdate;
}
