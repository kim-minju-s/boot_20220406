package com.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.ItemEntity;
import com.example.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired EntityManagerFactory emf;
	@Autowired ItemRepository iRepository;
	

	// 물품 일괄 추가
	@Override
	public int insertItemBath(List<ItemEntity> list) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			// 여러개 추가
			
			for(ItemEntity item : list) {
				em.persist(item);
			}
			
			em.getTransaction().commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return 0;			
		}
	}


	// 물품번호에 해당하는 물품 여러개 가져오기
	@Override
	public List<ItemEntity> selectItemEntityIn(Long[] no) {
		
		return iRepository.findByIcodeIn(no);
	}


	// 물품 일괄 수정
	@Override
	public int updateItemBatch(List<ItemEntity> list) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			System.out.println(list);
			for(ItemEntity item : list) {
				
				
				// 기본키를 이용해서 기존 데이터를 꺼냄
				ItemEntity oldItem = em.find(ItemEntity.class, item.getIcode());
				
				oldItem.setIname(item.getIname());
				oldItem.setIcontent(item.getIcontent());
				oldItem.setIprice(item.getIprice());
				oldItem.setIquantity(item.getIquantity());
				
				em.persist(oldItem);
			}
			
			em.getTransaction().commit();
			return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return 0;			
		}
	}


	// 물품 일괄 삭제
	@Override
	public int deleteItemBatch(Long[] no) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			for(Long tmp : no) {
				ItemEntity oldItem = em.find(ItemEntity.class, tmp);
				em.remove(oldItem);
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
