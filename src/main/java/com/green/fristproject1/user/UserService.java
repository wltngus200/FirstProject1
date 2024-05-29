package com.green.fristproject1.user;

import com.green.fristproject1.common.CustomFileUtils;
import com.green.fristproject1.user.common.*;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
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

        try {
            String path = String.format("user/%d", p.getUserId());
            String target = String.format("%s/%s", path, fileName);
            utils.makeFolder(path);
            utils.transfer(target, pic);
        } catch (Exception e) {
            throw new RuntimeException("가입에 실패했습니다.");
        }
        return mapper.signUpUser(p);
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

    int updateUpw(ChangeUpwReq p) {
        SignInReq req = SignInReq.builder().uid(p.getUid()).upw(p.getUpw()).build();
        UserEntity user = mapper.signInUser(req); //바꾸기 전, 재 로그인

        String hashPass = BCrypt.hashpw(p.getNewPw(), BCrypt.gensalt());
        p.setUpw(hashPass);
        return mapper.updateUpw(p);
    }

    int updatePic(MultipartFile pic, ChangePicReq p) {
        SignInReq req = SignInReq.builder().uid(p.getUid()).upw(p.getUpw()).build();
        UserEntity user = mapper.signInUser(req); //바꾸기 전, 재 로그인

        String randomName = utils.makeRandomFileName(pic);
        String path = String.format("user/%d", user.getUserId());
        String target = String.format("%s/%s", path, randomName);
        try {
            utils.makeFolder(path);
            utils.transfer(target, pic);
        } catch (Exception e) {
            throw new RuntimeException("프로필 업데이트에 실패했습니다.");
        }
        return mapper.updatePic(p);
    }

    int deleteUserInfo(DeleteInfoReq p) {
        return mapper.deleteUserInfo(p);
    }

    UserEntity getUserInfo(long userId) {
        return mapper.getUserInfo(userId);
    }
}
