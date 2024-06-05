package com.green.fristproject1.user.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInRes {
    private long userId;
    private String nm;
    private String createdAt;
    private String updatedAt;
    private String email;
    private String picName;
}
