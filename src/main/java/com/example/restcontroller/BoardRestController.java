package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BoardDTO;
import com.example.entity.BoardEntity;
import com.example.mapper.BoardMapper;
import com.example.repository.BoardRepository;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {
	
	// 정의 변수 설정
	@Value("${board.page.count}") int PAGECNT;

	@Autowired BoardMapper bMapper;
	@Autowired BoardRepository bRepository;
	
	//다음글
	// 127.0.0.1:9090/ROOT/api/board/nextboard?no=
	@RequestMapping(value="/nextboard", 
			method = {RequestMethod.GET},
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> boardselect1next(@RequestParam(name = "no") long no){
		Map<String, Object> map = new HashMap<>();
		try {
			
			BoardEntity board = bRepository.findFirst1ByNoGreaterThanOrderByNoAsc(no);
			if(board != null) {
				map.put("status", 200);
				map.put("result", board);				
			}
		} catch (Exception e) {
			map.put("status", 0);
		}
		return map;
	}
	
	// 127.0.0.1:9090/ROOT/api/board/updatehit1?no=
	@RequestMapping(value="/updatehit1", 
			method = {RequestMethod.PUT},
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> boardUpdate1PUT(@RequestParam(name = "no") long no){
		Map<String, Object> map = new HashMap<>();
		try {
			
			BoardEntity board = bRepository.findById(no).orElse(null);
			board.setHit(board.getHit() + 1L);
			bRepository.save(board);
			map.put("status", 200);
			
		} catch (Exception e) {
			map.put("status", 0);
		}
		return map;
	}
	
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
	
	// 게시글 1개 조회
	// 127.0.0.1:9090/ROOT/api/board/selectone?bno=
	@RequestMapping(value="/selectone", 
			method = {RequestMethod.GET},
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> boardSelectOneGET(
			@RequestParam(name="bno") long bno ){	//@ModelAttribute 대신 사용
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		
		BoardDTO retBoard = bMapper.selectBoardOne(bno);
		if (retBoard != null) {
			map.put("status", 200);
			map.put("result", retBoard);
		}
		return map;
	}
	
	// 게시글 목록(페이지네이션)
	// 127.0.0.1:9090/ROOT/api/board/selectlist?page=1
	@RequestMapping(value="/selectlist", 
			method = {RequestMethod.GET},
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> boardSelectListGET(
			@RequestParam(name="page", defaultValue ="1") int page ){	//@ModelAttribute 대신 사용
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		
		List<BoardDTO> list = bMapper.selectBoardList((page*PAGECNT)-(PAGECNT-1), page*PAGECNT);
		if (list != null) {
			map.put("status", 200);
			map.put("result", list);
		}
		return map;
	}
	
	// 조회수 증가
	// 127.0.0.1:9090/ROOT/api/board/updatehit?bno=
	@RequestMapping(value="/updatehit", 
			method = {RequestMethod.PUT},
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> boardUpdateHitPUT(
			@RequestParam(name="bno") long bno){	//@ModelAttribute 대신 사용
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		
		int ret = bMapper.updateBoardHitOne(bno);
		if (ret == 1) {
			map.put("status", 200);
		}
		return map;
	}
	
}
