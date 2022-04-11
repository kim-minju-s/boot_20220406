package com.example.boot_20220406;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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

// 엔티티 등록(jpa) == DTO(Mybatis)
@EntityScan(basePackages = {"com.example.entity"})

// 저장소(jpa) == Mapper(Mybatis)
@EnableJpaRepositories(basePackages = {"com.example.repository"})

public class Boot20220406Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20220406Application.class, args);
	}

}
