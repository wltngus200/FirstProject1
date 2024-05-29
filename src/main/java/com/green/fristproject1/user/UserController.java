package com.green.fristproject1.user;

import com.green.fristproject1.common.ResultDto;
import com.green.fristproject1.user.common.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    public ResultDto<Integer> signUpUser(@RequestPart MultipartFile pic, @RequestPart SignUpReq p){
        int result=service.signUpUser(pic, p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultData(result)
                .resultMsg("회원가입에 실패하였습니다.")
                .build();

    }
    @PostMapping("sign-in")
    public ResultDto<SignInRes> signInUser(@ModelAttribute @ParameterObject SignInReq p){
        SignInRes result=service.signInUser(p);
        return ResultDto.<SignInRes>builder()
                .statusCode(HttpStatus.OK)
                .resultData(result)
                .resultMsg("로그인에 성공하였습니다.")
                .build();
    }
    @PutMapping("password")
    public ResultDto<Integer> updateUpw(@ModelAttribute @ParameterObject ChangeUpwReq p){
        int result=service.updateUpw(p);
        return ResultDto.<Integer>builder()
                .resultMsg("비밀번호를 성공적으로 변경하였습니다.")
                .resultData(result)
                .statusCode(HttpStatus.OK)
                .build();
    }
    @PutMapping("pic")
    public ResultDto<Integer> updatePic(@RequestPart MultipartFile pic, @RequestPart ChangePicReq p){
        int result=service.updatePic(pic, p);
        return ResultDto.<Integer>builder()
                .resultData(result)
                .statusCode(HttpStatus.OK)
                .resultMsg("사진을 성공적으로 업데이트 하였습니다.")
                .build();
    }

    @DeleteMapping
    public ResultDto<Integer> deleteUserInfo(@ModelAttribute @ParameterObject DeleteInfoReq p){
        int result=service.deleteUserInfo(p);
        return ResultDto.<Integer>builder()
                .resultMsg("")
                .statusCode(HttpStatus.OK)
                .resultData(result)
                .build();
    }

    @GetMapping
    public ResultDto<UserEntity> getUserInfo(@RequestParam long userId){
        UserEntity user=service.getUserInfo(userId);
        return ResultDto.<UserEntity>builder()
                .statusCode(HttpStatus.OK)
                .resultData(user)
                .resultMsg("")
                .build();
    }
}
