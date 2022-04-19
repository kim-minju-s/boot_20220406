package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.ProductEntity;
import com.example.entity.ProductViewEntity;
import com.example.repository.ProductRepository;
import com.example.repository.ProductViewRepository;
import com.example.service.ProductService;

@RestController

// 앱으로 만들 경우 앱에서 데이터를 가져갈 수 없음
@CrossOrigin("*")	// vue를 다른 다른 서버로 돌리고 있을 때 사용
@RequestMapping(value = "/api/product")
public class ProductRestController {
	
	@Autowired ProductViewRepository pvRepository;
	@Autowired ProductRepository pRepository;
	@Autowired ProductService pService;
	
	
	
	// 물품입출고 내역을 이용한 재고수량
	// 127.0.0.1:9090/ROOT/api/product/selectone.json?no=1009
	@GetMapping(value = "/selectone.json",
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> selectOneGET(
			@RequestParam(name = "no") long no){
		Map<String, Object> map = new HashMap<>();
		try {
			
			ProductViewEntity pvEntity = pvRepository.findById(no).orElse(null);
			
			map.put("status", 200);
			map.put("result", pvEntity);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	
	// 이미지 1개 추가
	// 127.0.0.1:9090/ROOT/api/product/insert.json
	@PostMapping(value = "/insert.json",
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> insertPOST(
			@ModelAttribute ProductEntity product,
			@RequestParam(name = "file", required = false) MultipartFile file){
			// required = false 첨부해도 되고 안해도 됨.
		Map<String, Object> map = new HashMap<>();
		try {
			if (file != null) {
				if (!file.isEmpty()) {
					product.setImagedata(file.getBytes());
					product.setImagename(file.getOriginalFilename());
					product.setImagetype(file.getContentType());
					product.setImagesize(file.getSize());
				}
			}
			pRepository.save(product);
			
			map.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	// 이미지 여러개 추가
	// 127.0.0.1:9090/ROOT/api/product/insertbatch.json
	@PostMapping(value = "/insertbatch.json",
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> insertBatchPOST(
			@RequestParam(name = "name") String[] name,
			@RequestParam(name = "price") Long[] price,
			@RequestParam(name = "file", required = false) MultipartFile[] file){
			// required = false 첨부해도 되고 안해도 됨.
		Map<String, Object> map = new HashMap<>();
		try {
			// List 객체 생성
			List<ProductEntity> list = new ArrayList<>();
			
			for(int i=0;i<name.length;i++) {
				System.out.println(name[i]);
				System.out.println(price[i] );
				System.out.println(file[i].getOriginalFilename());
				
				// Entity 생성
				ProductEntity product = new ProductEntity();
				product.setName(name[i]);
				product.setPrice(price[i]);
				
				product.setImagedata(file[i].getBytes());
				product.setImagename(file[i].getOriginalFilename());
				product.setImagesize(file[i].getSize());
				product.setImagetype(file[i].getContentType());
				
				// 데이터 하나하나를 list에 담는다
				list.add(product);
				
				map.put("status", 200);
			}
			// 데이터들이 전부 Entity에 들어간 후
			pService.insertBatch(list);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}
	
	// 수정하기(이미지 X)
	@PutMapping(value = "/update.json",
			consumes = {MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Object> updatePatch(
			@RequestBody ProductEntity product){
		
		Map<String, Object> map = new HashMap<>();
		try {
			// 1개 꺼내기
			ProductEntity product1 = pRepository.findById(product.getNo()).orElse(null);
			
			// 필요한 정보 변경
			product1.setName(product.getName());
			product1.setPrice(product.getPrice());
			pRepository.save(product1);
			
			map.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		return map;
	}

}
