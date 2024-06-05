package com.green.fristproject1.user;

import com.green.fristproject1.common.CustomFileUtils;
import com.green.fristproject1.user.common.*;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.green.fristproject1.user.common.IDCheck.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CustomFileUtils utils;

    @Transactional
    int signUpUser(MultipartFile pic, SignUpReq p) {
        /*if(!isValidId(p.getUid())) {//정규식에 어긋남
            //if는 괄호 안이 true일 경우 실행
            //정규식에 맞으면 true를 출력
            //(!false)=>TRUE 즉, 정규식이 안 맞으면 실행으로 바뀌는 것
              //(!true)=>FALSE => 실행 X
            throw new RuntimeException("올바르지 않은 아이디입니다.");
        }
        if(mapper.searchUser(p.getUid())!=0){
            throw new RuntimeException("중복된 아이디입니다.");
        }
        if(!isValidPassword(p.getUpw())){ //정규식에 어긋남
            throw new RuntimeException("비밀번호에 허용되지 않은 특수문자가 사용되었습니다.");
        }
        if(!isValidEmail(p.getEmail())){
            throw new RuntimeException("올바르지 않은 이메일입니다.");
        }*/

        String hashPass = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        p.setUpw(hashPass);
        String fileName = utils.makeRandomFileName(pic);
        p.setPicName(fileName);
        int result=mapper.signUpUser(p);

        try {
            String path = String.format("user/%d", p.getUserId());
            String target = String.format("%s/%s", path, fileName);
            utils.makeFolder(path);
            utils.transfer(target, pic);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("가입에 실패했습니다.");
        }
        return result;
    }

    SignInRes signInUser(SignInReq p) {
        UserEntity user = mapper.signInUser(p);
        if (user == null) {
            throw new RuntimeException("아이디가 틀렸습니다.");
        } else if (!BCrypt.checkpw(p.getUpw(), user.getUpw())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }
        SignInRes res = SignInRes.builder()
                .userId(user.getUserId())
                .nm(user.getNm())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .email(user.getEmail())
                .picName(user.getPicName())
                .build();
        return res;
    }
    //저장된 이메일 통해서 비밀번호 재발급과 비밀번호 바꾸기... 다른 개념?
    int updateUpw(ChangeUpwReq p) {
        SignInReq req = SignInReq.builder().uid(p.getUid()).upw(p.getUpw()).build();
        signInUser(req);//바꾸기 전, 재 로그인으로 본인 확인
        //리턴 없이
        String newHashPass = BCrypt.hashpw(p.getNewPw(), BCrypt.gensalt());
        p.setNewPw(newHashPass);

        return mapper.updateUpw(p);
    }

    int updatePic(MultipartFile pic, ChangePicReq p) {
        SignInReq req = SignInReq.builder().uid(p.getUid()).upw(p.getUpw()).build();
        SignInRes res=signInUser(req); //바꾸기 전, 재 로그인
        // 로그인 처리가 없어서 UserId를 얻기 위함(낭비) //long signedUserId를 입력 받아도 될 듯?

        String randomName = utils.makeRandomFileName(pic);
        p.setPicName(randomName); //랜덤이름을 DB에 전송

        String path = String.format("/user/%d", res.getUserId()); //userId 필요
        String target = String.format("%s/%s", path, randomName);
        try {
            utils.deleteFolder(path); //지우고
            utils.makeFolder(path); //다시 넣고
            utils.transfer(target, pic); //전송
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("프로필 업데이트에 실패했습니다.");
        }
        return mapper.updatePic(p);

        //폴더 변경 페이지에 삭제하고 저장 버튼 기능도 있어야함
    }

    int deleteUserInfo(long userId) {
        String shortPath=String.format("/user/%d", userId);
        utils.deleteFolder(shortPath);
        return mapper.deleteUserInfo(userId);
        //탈퇴가 진짜 다시 로그인 시켜볼 필요가 있나???
    }

    UserEntity getUserInfo(long userId) {
        return mapper.getUserInfo(userId);
    }
}
