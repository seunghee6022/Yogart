package com.ssafy.yogart.user.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.yogart.teachers.repository.PtInfoRepository;
import com.ssafy.yogart.user.error.AlreadyExistsException;
import com.ssafy.yogart.user.model.User;
import com.ssafy.yogart.user.model.UserFile;
import com.ssafy.yogart.user.repository.UserFileRepository;
import com.ssafy.yogart.user.repository.UserRepository;
import com.ssafy.yogart.user.service.JwtService;
import com.ssafy.yogart.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserFileRepository userFileRepository;

	@Autowired
	private PtInfoRepository ptInfoRepository;
	
	@Autowired
	private JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
	public User login(String email, String password) {
    	User user = userRepository.findByUserEmailAndUserPassword(email, password);
//    	if(user == null)
//    			throw new AlreadyExistsException("Wrong information!");
		return user;
	}
    
    // 가입
    @Override
    public User join(String email, String name, String nickname, String password) {
    	User user = userRepository.findByUserEmail(email);
    	if (user != null)
    		throw new AlreadyExistsException("Duplicate useremail");
        return userRepository.save(new User(email, name, nickname, password));
    }
    
    public User joinSocial(String email, String nickname, String randPass, String loginMethod) {
    	User user = userRepository.findByUserEmailAndLoginMethod(email, loginMethod);
    	if (user != null)
    		throw new AlreadyExistsException("Duplicate useremail");
    	return userRepository.save(new User(email, nickname, nickname, randPass, loginMethod));
    }


	// 인증 & 개인정보 조회
    @Override
    public User authentication(String token) {
    	if(jwtService.isUsable(token)) {
//    		Map<String, Object> result = jwtService.get(token);
    		User userTemp = jwtService.get(token);
//    		User user = userRepository.findByUserEmailAndUserPassword((String)result.get("UserEmail")
//    																,(String)result.get("UserPassword"));
    		User user = userRepository.findByUserEmailAndUserPassword(userTemp.getUserEmail(),userTemp.getUserPassword());
    		return user;
    	}
    	return null;
    }

    // 탈퇴
    @Override
    public void withdraw(User user) {
        userRepository.delete(user);
    }

	@Override
	public User loginSocial(String email, String loginMethod) {
		User user = userRepository.findByUserEmailAndLoginMethod(email, loginMethod);
		return user;
	}
	
	@Override
	public User emailChk(String email) {
		User user = userRepository.findByUserEmail(email);
		return user;
	}

	@Override
	public User nicknameChk(String nickname) {
		User user = userRepository.findByUserNickname(nickname);
		return user;
	}

	@Override
	public List<UserFile> getRegistrationUsers() {
		return userFileRepository.getUsers();
	}

	@Override
	public List<UserFile> getRegistrationImage(String userEmail) {
		return userFileRepository.getImageName(userEmail);
	}

	@Override
	public void registerUserToTeacher(String userEmail) {
		userFileRepository.deleteByUserFileEmail(userEmail);
	}
	
	public List<User> showAllTeacherlist() {
		return userRepository.findByUserAuthority("TEACHER");
	}
	
	@Override
	public User findUser(int id) {
		return userRepository.findOneById(id);
	}

	@Override
	public User updateInfo(User user) {
		return userRepository.save(user);
	}

}
