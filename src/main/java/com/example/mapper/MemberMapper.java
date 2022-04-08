package com.example.mapper;

import com.example.dto.MemberDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {
    
    // INSERT INTO 테이블명(칼럼들..) VALUES (추가할 값들..)
    @Insert({" INSERT INTO MEMBER",
            " (UEMAIL, UPW, UNAME, UPHONE, UROLE, UREGDATE)",
            " VALUES(#{obj.uemail}, #{obj.upw}, #{obj.uname}, #{obj.uphone}, #{obj.urole}, CURRENT_DATE)"})
    public int memberJoin( @Param(value="obj") MemberDTO member);

    // SELECT 컬럼명들 FROM 테이블명 WHERE 조건 AND 조건
    @Select({"SELECT UEMAIL, UNAME, UROLE FROM MEMBER",
            " WHERE UEMAIL=#{email} AND UPW=#{pw}"})
    public MemberDTO memberLogin(
            // Param 의 value가 변수, String email은 받는값 
            @Param(value = "email") String email,
            @Param(value = "pw") String pw);

    // MemberDetails
    // SELECT 컬럼명들 FROM 테이블명 WHERE 조건 AND 조건
    @Select({"SELECT UEMAIL, UPW, UNAME, UPHONE, UROLE FROM MEMBER",
            " WHERE UEMAIL=#{email}"})
    public MemberDTO memberEmail(
            // Param 의 value가 변수, String email은 받는값 
            @Param(value = "email") String email);

    
    @Update({
    	"UPDATE MEMBER SET UPW=#{upw} WHERE UEMAIL=#{email}"
    })
    public int memberUpdatePw(
    		@Param(value="email") String email,
    		@Param(value="upw") String upw);
    
    @Update({
    	"UPDATE MEMBER SET UNAME=#{name}, UPHONE=#{phone} WHERE UEMAIL=#{email}"
    })
    public int updateMember(
    		@Param("email") String email, 
    		@Param("name") String name, 
    		@Param("phone") String phone);
    
    
    @Update({
    	"UPDATE MEMBER SET UPW='X', UNAME=NULL, UPHONE=NULL, UROLE=NULL WHERE UEMAIL=#{email}"
    })
    public int deleteMember(@Param("email") String email);
}
