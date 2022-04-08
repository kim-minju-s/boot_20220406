package com.example.boot_20220406;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication

// 정의 변수 설정
@PropertySource("classpath:global.properties")

// filter 적용시키기
@ServletComponentScan(basePackages = {
		"com.example.filter"
})

// 컨트롤러, 환경설정, 서비스 파일 등록
@ComponentScan(basePackages = {
		"com.example.controller",
		"com.example.config",
		"com.example.service",
		"com.example.restcontroller",
		"com.example.jwt"
})
// 매퍼 등록
@MapperScan(basePackages= {
		"com.example.mapper"
})
public class Boot20220406Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20220406Application.class, args);
	}

}
