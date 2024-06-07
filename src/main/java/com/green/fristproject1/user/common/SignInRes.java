package com.green.fristproject1.user.common;

import io.swagger.v3.oas.annotations.media.Schema;
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
}
