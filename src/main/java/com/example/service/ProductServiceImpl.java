package com.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.ProductEntity;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired EntityManagerFactory emf;

	// 이미지 일괄 추가
	@Override
	public int insertBatch(List<ProductEntity> list) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			
			for(ProductEntity product : list) {
				em.persist(product);
			}
			
			em.getTransaction().commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return 0;
		}
	}
	
}
