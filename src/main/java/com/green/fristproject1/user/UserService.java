package com.green.fristproject1.user;

import com.green.fristproject1.common.CustomFileUtils;
import com.green.fristproject1.user.common.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CustomFileUtils utils;

    @Transactional
    int signUpUser(MultipartFile pic, SignUpReq p) {
        String hashPass = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        p.setUpw(hashPass);
        String fileName = utils.makeRandomFileName(pic);
        p.setPic(fileName);
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
                .pic(user.getPic())
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
        SignInRes res=signInUser(req); //바꾸기 전, 재 로그인 // getUserId를 얻기 위함(낭비)

        String randomName = utils.makeRandomFileName(pic);
        p.setPic(randomName); //랜덤이름을 DB에 전송

        String path = String.format("user/%d", res.getUserId());
        String target = String.format("%s/%s", path, randomName);
        try {
            utils.makeFolder(path);
            utils.transfer(target, pic);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("프로필 업데이트에 실패했습니다.");
        }
        return mapper.updatePic(p);
        //폴더 및 지난 사진 삭제는 아직
        //폴더 변경 페이지에 삭제하고 저장 버튼 기능도 있어야함
    }

    int deleteUserInfo(long userId) {
        return mapper.deleteUserInfo(userId);
        //탈퇴가 진짜 다시 로그인 시켜볼 필요가 있나???
    }

    UserEntity getUserInfo(long userId) {
        return mapper.getUserInfo(userId);
    }
}
