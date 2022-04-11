package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.BoardEntity;
import com.example.repository.BoardRepository;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired BoardRepository bRepository;
	
	@Value("${board.page.count}") int PAGECNT;
	
	@GetMapping(value = "/insert")
	public String insertGET() {
		return "/board/insert";
	}
	
	@PostMapping(value = "/insert")
	public String insertPOST(@ModelAttribute BoardEntity board) {
		
		// save(entity객체) == INSERT INTO
		bRepository.save(board);
		return "redirect:/board/insert";
	}
	
	@GetMapping(value = "/selectlist")
	public String selectlistGET(Model model,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "title", defaultValue = "") String title) {
		
		// 페이지네이션(시작페이지(0부터), 갯수)
		PageRequest pageRequest = PageRequest.of(page-1, PAGECNT);
		
		// 검색어, 페이지네이션
		List<BoardEntity> list = bRepository.findByTitleContainingOrderByNoDesc(title, pageRequest);
		
		model.addAttribute("list", list);
		
		// 10 => 1, 23 => 3, 45 => 5
		long total = bRepository.countByTitleContaining(title);
		model.addAttribute("pages", (total-1)/PAGECNT+1);
		
		return "/board/selectlist";
	}
	
	@GetMapping(value = "/selectone")
	public String selectoneGET(Model model,
			@RequestParam(name = "no") long no) {
		
		BoardEntity board = bRepository.findById(no).orElse(null);
		model.addAttribute("board", board);
		return "/board/selectone";
	}
	
}
