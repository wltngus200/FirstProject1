package com.green.fristproject1.user.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePicReq {

    private String uid;
    private String upw;

    @JsonIgnore
    private String picName;
}
