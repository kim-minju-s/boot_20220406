package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ItemDTO;
import com.example.mapper.ItemMapper;

@Controller
@RequestMapping(value="/seller")
public class SellerController {
	
	// 정의 변수 설정
	@Value("${board.page.count}") int PAGECNT;
	
	@Autowired ItemMapper iMapper;
	
	// 127.0.0.1:9090/ROOT/seller/home?page=1&txt=검색어
	@GetMapping(value= {"/", "/home"})
	public String sellerGET(
			Model model,
			@RequestParam(name="page", defaultValue ="1") int page,
			@RequestParam(name="txt", defaultValue="") String txt,
			@AuthenticationPrincipal User user) {
		if (user != null) {
			List<ItemDTO> list =
					iMapper.selectItemList(user.getUsername(), txt, (page*PAGECNT)-(PAGECNT-1), page*PAGECNT);
			
			model.addAttribute("list", list);
			
			// 3 -> 1 , 15 -> 2
			long cnt = iMapper.selectItemCount(txt, user.getUsername());
			model.addAttribute("pages", (cnt-1)/PAGECNT+1);
			
			return "/seller_home";			
		}
		return "redirect:/member/login";
	}
	
	// 물품추가 화면
	@GetMapping(value="/insertitem")
	public String sellerInsertGET() {
		return "/seller/insertitem";
	}
	
	// 판매자가 물품 등록하기
	@PostMapping(value="/insertitemaction")
	public String sellerInsertPOST(
			@AuthenticationPrincipal User user,
			@ModelAttribute ItemDTO item,
			@RequestParam(name="timage") MultipartFile file) throws IOException{
		
		System.out.println(item.toString());
		System.out.println(file.getOriginalFilename());
		if (user != null) {
			item.setIimage(file.getBytes());
			item.setIimagename(file.getOriginalFilename());
			item.setIimagesize(file.getSize());
			item.setIimagetype(file.getContentType());
			
			// redis DB의 세션에 있는 아이디 정보 가져오기
			item.setUemail(user.getUsername());
			
			int ret = iMapper.insertItemOne(item);
			if (ret == 1) {
				return "redirect:/seller/home";		
			}
			return "redirect:/seller/insertitem";
					
		}
		return "redirect:/seller/login";
	}
	
	// 삭제하기
	@PostMapping(value="/deleteitem")
	public String deleteitemPOST(
			@AuthenticationPrincipal User user,
			@RequestParam(name="code") long code) {
		
		System.out.println(user.getUsername());
		if (user != null) {
			System.out.println(code);
			iMapper.deleteItemOne(code, user.getUsername());
			return "redirect:/seller/home";
		}
		
		return "redirect:/member/login";
	}
	
	//수정하기 화면
	@GetMapping(value="/updateitem")
	public String updateitemGET(
			Model model,
			@AuthenticationPrincipal User user,
			@RequestParam(name="code") long code) {
		
		if (user != null) {
			ItemDTO item = iMapper.selectItemOne(code);
			model.addAttribute("item", item);
			return "/seller/updateitem";
		}
		return "/seller/updateitem";
	}
	
	@PostMapping(value="/updateitemaction")
	public String updateitemPOST(
			Model model,
			@AuthenticationPrincipal User user,
			@ModelAttribute ItemDTO item,
			@RequestParam(name="timage") MultipartFile file) throws IOException {
		
		System.out.println(item.toString());
		System.out.println(user.getUsername());
		if (user != null) {
			if (!file.isEmpty()) {
				item.setIimage(file.getBytes());
				item.setIimagename(file.getOriginalFilename());
				item.setIimagesize(file.getSize());
				item.setIimagetype(file.getContentType());				
			}
			
			// redis DB의 세션에 있는 아이디 정보 가져오기
			item.setUemail(user.getUsername());

			iMapper.updateItemOne(item);

			// 알림창이 필요시
			model.addAttribute("msg","물품변경 완료");
			model.addAttribute("url","/seller/home");
			return "/seller/alert";
//			return "redirect:/seller/home";
		}
		
		return "redirect:/member/login";
	}
}
