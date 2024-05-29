package com.green.fristproject1.user;

import com.green.fristproject1.user.common.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int signUpUser(SignUpReq p);
    UserEntity signInUser(SignInReq p); //selectUserId

    int updateUpw(ChangeUpwReq p);
    int updatePic(ChangePicReq p);
    int deleteUserInfo(DeleteInfoReq p);
    UserEntity getUserInfo(long userId);
}
