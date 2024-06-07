package com.green.fristproject1.user.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserEntity {
    private long userId;
    private String uid;
    private String upw;
    private String nm;
    private String createdAt;
    private String updatedAt;
    private String email;
    //SELECT 유저가 입력한 아이디를 바탕으로 유저 정보 가져옴
}
