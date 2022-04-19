package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.ProductEntity;

@Service
public interface ProductService {

	// 이미지 일괄 추가
	public int insertBatch(List<ProductEntity> list);
}
