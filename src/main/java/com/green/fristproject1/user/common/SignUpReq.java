package com.green.fristproject1.user.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SignUpReq {
    @JsonIgnore
    private long userId;

    private String uid;
    private String upw;
    private String nm;
    private String email;

    @JsonIgnore
    private String pic;
}
