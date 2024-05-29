package com.green.fristproject1.user.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ChangeUpwReq {
    private String uid;
    private String upw;
    private String newPw;
    private MultipartFile newPic;
    //SQL문의 IF에 들어가는 애는??
    //폴더도 지워야되니 메소드 분리가 나으려나
}
