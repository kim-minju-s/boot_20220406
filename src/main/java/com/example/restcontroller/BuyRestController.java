package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.BuyEntity;
import com.example.entity.BuyProjection;
import com.example.entity.ItemEntity;
import com.example.entity.MemberEntity;
import com.example.repository.BuyRepository;



@RestController
@RequestMapping(value = "/api/buy")
public class BuyRestController {

	@Autowired BuyRepository buyRepository;
	
	// 127.0.0.1:9090/ROOT/api/buy/insert
	// { bcnt:2, item:{icode:3}, member:{uemail:a1} }
	@RequestMapping(value="/insert", 
			method = {RequestMethod.POST},	// POST로 받음
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> buyInsertPOST(
			@RequestBody Map<String, Object> buy){
		// buyEntity에 bcnt값을 넣음
		BuyEntity buyEntity = new BuyEntity();
		buyEntity.setCnt(((Integer)buy.get("bcnt")).longValue());
		// buyEntity.setBcnt(Long.parseLong( buy.get("bcnt").toString() ));
		
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setIcode(((Integer)buy.get("icode")).longValue());
		// itemEntity.setIcode(Long.parseLong(buy.get("icode").toString()));
		buyEntity.setItem(itemEntity);
		
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setUemail((String)buy.get("uemail"));
		buyEntity.setMember(memberEntity);
		
		buyRepository.save(buyEntity);
		
		System.out.println(buy.toString());
		Map<String, Object> map = new HashMap<>();
		map.put("status", 200);
		
		return map;
	}
	
	// 주문하기
	// 127.0.0.1:9090/ROOT/api/buy/insert1
	// { bcnt:2, item:{icode:3}, member:{uemail:a1} }
	@RequestMapping(value="/insert1", 
			method = {RequestMethod.POST},	// POST로 받음
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})	// 반환은 JSON타입으로
	public Map<String, Object> buyInsert1POST(
			@RequestBody BuyEntity buyEntity){
		

		System.out.println(buyEntity.toString());
		Map<String, Object> map = new HashMap<>();
		buyRepository.save(buyEntity);
		map.put("status", 200);
		
		return map;
	}
	
	
	// 주문 조회
	// 127.0.0.1:9090/ROOT/api/buy/selectone?bno=
	// { bcnt:2, item:{icode:3}, member:{uemail:a1} }
	@RequestMapping(value="/selectone", 
			method = {RequestMethod.GET},	// POST로 받음
			consumes = {MediaType.ALL_VALUE},	// 모든 타입을 다 받음
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> buyInsert1POST(
			@RequestParam(name = "bno") long bno){
		Map<String, Object> map = new HashMap<>();
		
//		BuyEntity buy = buyRepository.findById(bno).orElse(null);
		BuyProjection buy = buyRepository.findByNo(bno);
		map.put("result", buy);
		map.put("status", 200);
		
		return map;
	}
	
	// 회원 아이디로 주문 목록 불러오기
	// 127.0.0.1:9090/ROOT/api/buy/selectlist?uemail=
	@RequestMapping(value="/selectlist",
			method = {RequestMethod.GET},
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> buySelectListGET(
			@RequestParam(name = "uemail") String uemail){
		
		System.out.println(uemail);
		Map<String, Object> map = new HashMap<>();
		
		List<BuyProjection> list = buyRepository.findByMember_uemail(uemail);
		map.put("result", list);
		map.put("status", 200);
		
		return map;
	}
	
}
