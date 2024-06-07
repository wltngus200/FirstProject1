package com.green.fristproject1.user.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ChangeUpwReq {
    private String uid;
    private String upw;
    private String newPw;

}
