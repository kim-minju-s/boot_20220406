package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.example.dto.ItemDTO;

@Mapper
public interface ItemMapper {
	
	@Select({
        "SELECT*FROM (",
            "SELECT ",
                " I.ICODE, I.INAME, I.IPRICE, I.IQUANTITY, I.IREGDATE, ROW_NUMBER() OVER (ORDER BY I.ICODE DESC) ROWN", 
            " FROM ITEM I ",
            " WHERE I.INAME LIKE '%' || #{txt} || '%' ",
            " AND I.UEMAIL = #{email} ",
        ")WHERE ROWN BETWEEN #{start} AND #{end}"
	})
	public List<ItemDTO> selectItemList(
			@Param(value="email") String email,
			@Param(value="txt") String txt,
			@Param(value="start") int start,
			@Param(value="end") int end );
	
	@Select({
        "SELECT",
        " COUNT(*) CNT", 
	    " FROM ITEM I",
	    " WHERE I.INAME LIKE '%' || #{txt} || '%'",
	    " AND I.UEMAIL = #{email}"
	})
	public long selectItemCount(
			@Param(value="txt") String txt,
			@Param(value="email") String email);
	
	@Insert({
        "INSERT INTO", 
        " ITEM(ICODE, INAME, ICONTENT, IPRICE, IQUANTITY, IIMAGE, IIMAGESIZE, IIMAGETYPE, IIMAGENAME, UEMAIL)",
        " VALUES(",
        " SEQ_ITEM_ICODE.NEXTVAL, #{obj.iname}, #{obj.icontent}, #{obj.iprice}, #{obj.iquantity}, #{obj.iimage, jdbcType=BLOB},", 
        " #{obj.iimagesize}, #{obj.iimagetype}, #{obj.iimagename}, #{obj.uemail})"
	})
	public int insertItemOne(@Param(value="obj") ItemDTO item);
	
	@Select({
		"SELECT", 
	    "    ICODE, INAME, ICONTENT, IPRICE, IQUANTITY, IREGDATE",
	    " FROM ",
	    "    ITEM",
	    " WHERE ICODE = #{code}"
	})
	public ItemDTO selectItemOne(@Param(value="code") long code);
	
	@Results({
		@Result( column = "ICODE", property="icode"),
		@Result( column = "IIMAGE", property="iimage",
				jdbcType = JdbcType.BLOB, javaType=byte[].class)
	})
	
	@Select({
		"SELECT",
	    "    ICODE, IIMAGE, IIMAGESIZE, IIMAGETYPE, IIMAGENAME",
	    " FROM",
	    "    ITEM",
	    " WHERE ICODE = #{code}"
	})
	public ItemDTO selectItemImageOne(@Param(value="code") long code);
	
	@Delete({
		"DELETE FROM ITEM I WHERE ICODE=#{code} AND UEMAIL=#{email}"
	})
	public int deleteItemOne(@Param(value="code") long code,
				@Param(value="email") String email);
	
	@Update({
		"<script>",
			"UPDATE ITEM SET INAME=#{obj.iname}, ",
			"ICONTENT = #{obj.icontent}, IPRICE = #{obj.iprice}, ",
			" IQUANTITY=#{obj.iquantity} ",
			
			"<if test='obj.iimage != null'>",
				", IIMAGE=#{obj.iimage}, IIMAGESIZE=#{obj.iimagesize}, ",
				" IIMAGETYPE=#{obj.iimagetype}, IIMAGENAME=#{obj.iimagename} ",
	        "</if>",
	        
			" WHERE ICODE=#{obj.icode} AND UEMAIL=#{obj.uemail}",
		"</script>"
	})
	public int updateItemOne(@Param(value="obj") ItemDTO item);
}
