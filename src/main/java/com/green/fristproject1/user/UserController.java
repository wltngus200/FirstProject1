package com.green.fristproject1.user;

import com.green.fristproject1.common.ResultDto;
import com.green.fristproject1.user.common.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    @Operation(summary = "유저 회원가입",
            description = "<strong> 변수명 : uid </strong> <p> 회원 아이디 ex)abc1231 </p>"+"\n"+
                          "<strong> 변수명 : upw </strong> <p> 회원 비밀번호 ex)aa123 </p>" +"\n"+
                          "<strong> 변수명 : nm </strong> <p> 회원 이름 ex)홍길동 </p>"+"\n"+
                          "<strong> 변수명 : email </strong> <p> 회원 이메일 ex)abc1231@naver.com </p>")
    public ResultDto<Integer> signUpUser(@RequestBody SignUpReq p){
        try{
            int result=service.signUpUser(p);
            return ResultDto.<Integer>builder()
                    .statusCode(HttpStatus.OK)
                    .resultData(result)
                    .resultMsg("회원가입에 성공하였습니다.")
                    .build();
            // 이거 아닌 듯
        }catch (Exception e){
           e.printStackTrace();
            return ResultDto.<Integer>builder()
                    .statusCode(HttpStatus.OK)
                    .resultData(0)
                    .resultMsg("회원가입 실패.")
                    .build();
        }

    }
    @PostMapping("sign-in")
    @Operation(summary="유저 로그인",
            description = "<strong> 변수명 : uid </strong> <p> 회원 아이디 ex)abc1231 </p>"+"\n"+
                          "<strong> 변수명 : upw </strong> <p> 회원 비밀번호 ex)aa123 </p>" +"\n")
    public ResultDto<Long> signInUser(@ModelAttribute @ParameterObject SignInReq p){
        long result=service.signInUser(p);
        log.info("{},{}",p,result);
        return ResultDto.<Long>builder()
                .statusCode(HttpStatus.OK)
                .resultData(result)
                .resultMsg("로그인에 성공하였습니다.")
                .build();
    }
    @PutMapping("password")
    @Operation(summary="비밀번호 수정",
            description="<strong> 변수명 : uid </strong> <p> 회원 아이디 ex)abc1231 </p>"+"\n"+
                    "<strong> 변수명 : upw </strong> <p> 회원 비밀번호 ex)aa123 </p>" +"\n"+
                    "<strong> 변수명 : newPw </strong> <p> 새로운 비밀번호 ex)bb123 </p>"+"\n")
    public ResultDto<Integer> updateUpw(@ModelAttribute @ParameterObject ChangeUpwReq p){
        int result=service.updateUpw(p);
        return ResultDto.<Integer>builder()
                .resultMsg("비밀번호를 성공적으로 변경하였습니다.")
                .resultData(result)
                .statusCode(HttpStatus.OK)
                .build();
    }

    @DeleteMapping
    @Operation(summary="회원 탈퇴",
            description="<strong> 변수명 : user_id </strong> <p> 회원 PK ex)17 </p>")
    public ResultDto<Integer> deleteUserInfo(@RequestParam(name="user_id") long userId) {
        int result = service.deleteUserInfo(userId);
        return ResultDto.<Integer>builder()
                .resultMsg("탈퇴처리가 완료되었습니다.")
                .statusCode(HttpStatus.OK)
                .resultData(result)
                .build();
    }

    @GetMapping
    @Operation(summary="마이 페이지",
            description = "<strong> 변수명 : user_id </strong> <p> 회원 PK ex)17 </p>")
    public ResultDto<UserEntity> getUserInfo(@RequestParam(name="user_id") long userId){
        UserEntity user=service.getUserInfo(userId);
        return ResultDto.<UserEntity>builder()
                .statusCode(HttpStatus.OK)
                .resultData(user)
                .resultMsg("회원정보 열람")
                .build();
    }

    @GetMapping("/id_check")
    public ResultDto<String> searchUser(String uid){
        int yesOrNo=service.searchUser(uid);
        String minji=switch(yesOrNo){
            case 1 -> "중복된 아이디입니다.";
            case 0 -> "사용가능한 아이디입니다.";
            default -> "예기치 못한 에러가 발생했습니다.";
        };
        return ResultDto.<String>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(minji)
                .build();
    }
}
