package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.BuyEntity;
import com.example.entity.BuyProjection;

@Repository
public interface BuyRepository extends JpaRepository<BuyEntity, Long>{

	//findBy변수
	BuyProjection findByNo(Long bno);
	
	//findBy변수_하위변수
	// 회원 이메일로 조회
	List<BuyProjection> findByMember_uemail(String uemail);
	
	// 주문번호를 기준으로 내림차순 정렬
	// SELECT * FROM 테이블명 WHERE 1 ORDER BY NO ASC
	List<BuyProjection> findByOrderByNoAsc();
	
	// SELECT * FROM 테이블명 WHERE CNT >= ?
	List<BuyProjection> findByCntGreaterThanEqual(long bcnt);
	
	// SELECT * FROM 테이블명 WHERE NO=? AND CNT=?
	BuyProjection findByNoAndCnt(Long bno, Long bcnt);
	
	// SELECT * FROM 테이블명 WHERE NO IN (1,3,5)
	// SELECT * FROM 테이블명 WHERE NO=1 OR NO=3 OR NO=5
	List<BuyProjection> findByNoIn(List<Long> bno);
	
	@Query(value="SELECT * FROM BUY", nativeQuery = true)
	public List<BuyProjection> selectBuyList();
	
	@Query(value = "SELECT * FROM BUY WHERE NO=:no", nativeQuery = true)
	public BuyProjection selectBuyOne(@Param(value="no") long bno);
	
	@Query(value="SELECT * FROM BUY WHERE NO= :#{#obj.no} AND CNT=:#{#obj.cnt}", nativeQuery = true)
	public BuyProjection selectBuyOneAnd(@Param(value="obj") BuyEntity buy);
	
}
