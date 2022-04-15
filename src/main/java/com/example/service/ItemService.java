package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.ItemEntity;

@Service
public interface ItemService {
	
	// 일괄 추가
	public int insertItemBath(List<ItemEntity> list);
	
	// 수정시 해당하는 항목만 조회하기
	public List<ItemEntity> selectItemEntityIn(Long[] no);
	
	// 일괄 수정
	public int updateItemBatch(List<ItemEntity> list);
	
	// 일괄 삭제
	public int deleteItemBatch(Long[] no);
}
