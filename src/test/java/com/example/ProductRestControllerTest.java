package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.entity.ProductCountEntity;
import com.example.entity.ProductEntity;

public class ProductRestControllerTest {
    
    RestTemplate restTemplate;

    @BeforeEach
    public void setup(){
        restTemplate = new RestTemplate();
    }

    
    // 물품입출고 내역을 이용한 재고수량
    // 127.0.0.1:9090/ROOT/api/product/selectone.json?no=1009
    @Test
    public void selectOne() {
    	String url = "http://127.0.0.1:9090/ROOT/api/product/selectone.json?no=1009";
    	String result = restTemplate.getForObject(url, String.class);
    	System.out.println(result);
    	assertThat(result).contains("\"status\":200");
    }
    
    // 재고수량 조회
    // 127.0.0.1:9090/ROOT/api/productcount/selectcount.json
    @Test
    public void selectCountSUM() {
    	String url = "http://127.0.0.1:9090/ROOT/api/productcount/selectcount.json?no=1009";
    	String result = restTemplate.getForObject(url, String.class);
    	System.out.println(result);
    	assertThat(result).contains("\"status\":200");
    }
    
    // 물품 입.출고
    // 127.0.0.1:9090/ROOT/api/productcount/insert.json
    @Test
    public void insertProductCountTest(){
        // 전송하고자 하는 값 생성
        ProductCountEntity productCount = new ProductCountEntity();
        
        productCount.setCnt(-5L);
        productCount.setType("O");
        
        ProductEntity product = new ProductEntity();
        product.setNo(1009L);
        
        productCount.setProduct(product);

        // header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductCountEntity> entity 
            = new HttpEntity<>(productCount, headers);

        ResponseEntity<String> result = restTemplate.exchange(
            "http://127.0.0.1:9090/ROOT/api/productcount/insert.json", 
            HttpMethod.POST, 
            entity, 
            String.class
        );
        System.out.println(result.getBody());
        // System.out.println(result.toString());
        assertThat(result.getBody().toString()).contains("\"status\":200");
    }


    // 127.0.0.1:9090/ROOT/api/product/insert.json
    // @ModelAttribute
    @Test
    public void insertTest(){
        // 전송하고자 하는 값 생성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name","테스트용");
        body.add("price",1234L);

        // header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity< MultiValueMap<String, Object> > entity 
            = new HttpEntity<>(body, headers);

        ResponseEntity<String> result = restTemplate.exchange(
            "http://127.0.0.1:9090/ROOT/api/product/insert.json", 
            HttpMethod.POST, 
            entity, 
            String.class
        );

        assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");
    }

    // 127.0.0.1:9090/ROOT/api/product/update.json
    // @RequestBody
    @Test
    public void updateTest(){
        // 전송하고자 하는 값 생성
        ProductEntity product = new ProductEntity();
        product.setNo(1001L);
        product.setName("가나다");
        product.setPrice(444L);


        // header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductEntity> entity 
            = new HttpEntity<>(product, headers);

        ResponseEntity<String> result = restTemplate.exchange(
            "http://127.0.0.1:9090/ROOT/api/product/update.json", 
            HttpMethod.PUT, 
            entity, 
            String.class
        );

        assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");
    }
    
    
    
}
