package com.green.fristproject1.user.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
    private long userId;
    private String uid;
    private String upw;
    private String nm;
    private String createdAt;
    private String updatedAt;
    private String email;
    private String pic;
}
