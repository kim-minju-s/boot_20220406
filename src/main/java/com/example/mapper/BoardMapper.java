package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.BoardDTO;

@Mapper
public interface BoardMapper {

	@Insert({
		"INSERT INTO BOARD(",
			" BNO, BTITLE, BCONTENT, BHIT, BREGDATE, BTYPE, UEMAIL )",
		" VALUES(",
			" SEQ_BOARD_NO.NEXTVAL, #{brd.btitle}, #{brd.bcontent, ",
			" jdbcType=CLOB}, #{brd.bhit}, CURRENT_DATE, #{brd.btype}, #{brd.uemail})"
	})
	public int insertBoardOne(@Param(value="brd") BoardDTO board);
	
	
	@Delete({
		"DELETE FROM BOARD WHERE BNO = #{bno}"
	})
	public int deleteBoardOne(@Param(value="bno") long bno);
	
	
	@Update({
		"UPDATE BOARD SET",
        " BTITLE=#{obj.btitle}, BCONTENT=#{obj.bcontent} ",
		" WHERE BNO=#{obj.bno}"
	})
	public int updateBoardOne(@Param(value="obj") BoardDTO board);
	
	
	@Select({
		"SELECT * FROM BOARD WHERE BNO=#{bno}"
	})
	public BoardDTO selectBoardOne(@Param(value="bno") long bno);
	
	
	// 페이지네이션
	@Select({
		"SELECT * FROM (",
			" SELECT B.*, ROW_NUMBER() OVER (ORDER BY BNO DESC) ROWN", 
			" FROM BOARD B ",
		" ) WHERE ROWN BETWEEN #{start} AND #{end}"
	})
	public List<BoardDTO> selectBoardList(
				@Param(value="start") int s,
				@Param("end") int e);
	
	
	// 조회수 증가
	@Update({
		"UPDATE BOARD SET BHIT=BHIT+1 WHERE BNO=#{bno}"
	})
	public int updateBoardHitOne(@Param("bno") long bno);
	
}
