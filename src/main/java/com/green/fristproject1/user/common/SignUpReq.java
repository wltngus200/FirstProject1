package com.green.fristproject1.user.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class SignUpReq {
    @JsonIgnore //안 뜨니까 필요 없지?
    private long userId;

    private String uid;
    private String upw;
    private String nm;
    private String email;

    @JsonIgnore
    private String pic;
}
