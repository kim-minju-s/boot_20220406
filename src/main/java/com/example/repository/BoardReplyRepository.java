package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.BoardReplyEntity;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Long>{

	// 원본글 번호가 일치하는 댓글 개수
	List<BoardReplyEntity> findByBoard_noOrderByNoDesc(long bno);
}
