package com.green.fristproject1.user.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInReq {
    private String uid;
    private String upw;
}
