package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BoardDTO;
import com.example.mapper.BoardMapper;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

	@Autowired BoardMapper bMapper;
	
	// 127.0.0.1:9090/ROOT/api/board/insert
	@RequestMapping(value="/insert", 
			method = {RequestMethod.POST},	// POST로 받음
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> boardinsertPOST(
			@RequestBody BoardDTO board){	//@ModelAttribute 대신 사용
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		
		int ret = bMapper.insertBoardOne(board);
		if (ret == 1) {
			map.put("status", 200);
		}
		return map;
	}
	
	// 게시글 삭제하기
	// 127.0.0.1:9090/ROOT/api/board/delete
	@RequestMapping(value="/delete", 
			method = {RequestMethod.DELETE},
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> boardDeletePOST(
			@RequestParam(name="bno") long bno ){	//@ModelAttribute 대신 사용
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		
		int ret = bMapper.deleteBoardOne(bno);
		if (ret == 1) {
			map.put("status", 200);
		}
		return map;
	}
	
	// 게시글 수정하기
	// 127.0.0.1:9090/ROOT/api/board/update
	@RequestMapping(value="/update", 
			method = {RequestMethod.PUT},
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> boardupdatePUT(
			@RequestBody BoardDTO board ){	//@ModelAttribute 대신 사용
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		
		int ret = bMapper.updateBoardOne(board);
		if (ret == 1) {
			map.put("status", 200);
		}
		return map;
	}
}
