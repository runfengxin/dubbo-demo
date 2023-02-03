package com.xrf.dubbo.common;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xrf.dubbo.enums.UserSexEnum;

import java.io.Serializable;
import java.util.StringJoiner;

@TableName("users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "userName")
    private String userName;

    @TableField("passWord")
    private String passWord;
    private UserSexEnum userSex;
    private String nickName;
    private String blockId;

    public UserEntity() {
        super();
    }

    public UserEntity(String userName, String passWord, UserSexEnum userSex) {
        super();
        this.passWord = passWord;
        this.userName = userName;
        this.userSex = userSex;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserSexEnum getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSexEnum userSex) {
        this.userSex = userSex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userName='" + userName + "'")
                .add("passWord='" + passWord + "'")
                .add("userSex=" + userSex)
                .add("nickName='" + nickName + "'")
                .add("blockId='" + blockId + "'")
                .toString();
    }
}