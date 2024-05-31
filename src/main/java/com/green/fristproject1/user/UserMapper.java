package com.green.fristproject1.user;

import com.green.fristproject1.user.common.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int signUpUser(SignUpReq p);
    UserEntity signInUser(SignInReq p); //selectUserId

    int updateUpw(ChangeUpwReq p);
    int updatePic(ChangePicReq p);
    int deleteUserInfo(long userId);
    UserEntity getUserInfo(long userId);


    //아이디 중복검사에 활용!
    int searchUser(String uid);
}
