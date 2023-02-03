package com.xrf.dubbo.provider.mapper;

import com.xrf.dubbo.common.UserEntity;
import com.xrf.dubbo.enums.UserSexEnum;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends CommonMapper<UserEntity> {

    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    List<UserEntity> getAll();

    @Select("SELECT count(*) FROM users")
    int selectCount();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    UserEntity getOne(Long id);

    @Insert("INSERT INTO users(userName,passWord,user_sex,nick_name,block_id) VALUES(#{userName}, #{passWord}, #{userSex}, #{nickName}, #{blockId})")
    int insert(UserEntity user);

    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);

}