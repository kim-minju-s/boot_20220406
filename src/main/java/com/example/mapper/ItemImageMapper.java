package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.ItemImageDTO;

@Mapper
public interface ItemImageMapper {
	
	@Select({
		"SELECT IMGCODE FROM ITEMIMAGE WHERE ICODE=#{icode}"
	})
	public List<Long> selectItemImageList(@Param(value="icode") long code);
	
	@Select({
		"SELECT * FROM ITEMIMAGE WHERE IMGCODE = #{imgcode}"
	})
	public ItemImageDTO selectItemImageOne(@Param(value="imgcode") long code);
	
	@Update({
		"UPDATE ITEMIMAGE SET ",
	        " IIMAGE = #{obj.iimage, jdbcType=BLOB}, IIMAGENAME=#{obj.iimagename}, ",
	        " IIMAGETYPE=#{obj.iimagetype}, IIMAGESIZE=#{obj.iimagesize}",
        " WHERE IMGCODE = #{obj.imgcode}"
	})
	public int updateItemImageOne(@Param(value="obj") ItemImageDTO obj);
	
	@Insert({
		"<script>",
			"INSERT ALL ",
		        "<foreach collection='list' item='obj' separator=' '>",
		        " INTO ",
		        "    ITEMIMAGE(IMGCODE, IIMAGE, IIMAGESIZE, IIMAGETYPE, IIMAGENAME, ICODE) ",
		        " VALUES ",
		        "    (#{obj.imgcode}, #{obj.iimage, jdbcType=BLOB}, #{obj.iimagesize}, #{obj.iimagetype}, #{obj.iimagename}, #{obj.icode}) ",
		        "</foreach>",
	        " SELECT * FROM DUAL ",
		"</script>"
	})
	public int insertItemImageBatch(@Param(value="list") List<ItemImageDTO> list);
	
}
