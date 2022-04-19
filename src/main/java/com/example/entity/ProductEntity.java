package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "PRODUCT")
@SequenceGenerator(name = "SEQ3", 
	sequenceName = "SEQ_PRODUCT_NO", 
	allocationSize = 1,
	initialValue = 1001)
public class ProductEntity {

	@Id
	@GeneratedValue(generator = "SEQ3", strategy = GenerationType.SEQUENCE)
	Long no;
	
	@Column(length = 250)
	String name;
	
	Long price;
	
	@Lob
	@Column(nullable = true)
	byte[] imagedata;
	
	String imagename;
	
	String imagetype;
	
	Long imagesize = 0L;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss.SSS")
	@CreationTimestamp	// 데이터가 변경될 때도 시간이 바뀜
	private Date uptdate;
	
	@JsonBackReference	// 무시
	@OneToMany(mappedBy = "product")
	List<ProductCountEntity> list = new ArrayList<>();
	
}
