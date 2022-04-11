package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.BoardEntity;

@Repository
public interface BoardRepository 
	extends JpaRepository<BoardEntity, Long> {
	
	// 검색어가 포함된 전체 개수
	// SELECT COUNT(*) FROM BOARD10
	// WHERE BTITLE LIE '%' || '검색어' || '%'
	long countByTitleContaining(String title);

	// findBy컬럼명Containing
	// WHERE BTITLE LIE '%' || '검색어' || '%'
	List<BoardEntity> findByTitleContaining(String title);
	
	// findBy컬럼명ContainingOrderBy컬럼명DESC
	// SELECT * FROM 테이블명
	// WHERE BTITLE LIE '%' || '검색어' || '%' ORDER BY BNO DESC
	List<BoardEntity> findByTitleContainingOrderByNoDesc(String title);
	
	// 정렬 후 페이지별 조회
	// SELECT B.*, ROW_NUMBER() OVER( ORDER BY BNO DESC  ) FROM  BOARD10 B
	// WHERE BTITLE LIKE '%' || '검색어' || '%'  
	List<BoardEntity> findByTitleContainingOrderByNoDesc(String title, Pageable pageable);
	
	
	// 이전 글 ex) 20이면 작은것 중에서 가장 큰 값 19
	BoardEntity findTop1ByNoLessThanOrderByNoDesc(long no);
	
	// 다음 글 ex) 20이면 큰것 중에서 가장 작은것 21
	BoardEntity findFirst1ByNoGreaterThanOrderByNoAsc(long no);
	
	// native 쿼리문
	@Query(value = 
			"SELECT * FROM BOARD10 WHERE BTITLE LIKE %:ti%", 
			nativeQuery = true)
	List<BoardEntity> selectBoardList(@Param(value="ti") String title);
	
	
	
}
