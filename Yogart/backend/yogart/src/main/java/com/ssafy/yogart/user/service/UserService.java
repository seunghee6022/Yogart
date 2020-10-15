package com.ssafy.yogart.user.service;

import java.util.List;

import com.ssafy.yogart.user.model.User;
import com.ssafy.yogart.user.model.UserFile;

public interface UserService {
	// email 중복 체크
	User emailChk(String email);
	// nickname 중복 체크
	User nicknameChk(String nickname);
	
	// 로그인
	User login(String username, String password);
	User loginSocial(String email, String loginMethod);
	// 가입
    User join(String email, String name, String nickname, String password);
    User joinSocial(String email, String nickname, String randPass, String loginMethod);
    // 인증 & 개인정보 조회
    User authentication(String token);
    // 비밀번호 변경
    User updateInfo(User user);
    // 탈퇴
    
    List<UserFile> getRegistrationUsers(); // 강사 등록대기 유저 리스트 출력
    
    List<UserFile> getRegistrationImage(String userEmail);
    
    void registerUserToTeacher(String userEmail);
    
    void withdraw(User user);
    
    List<User> showAllTeacherlist();
    
    User findUser(int id);
}
