package com.ssafy.yogart.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.yogart.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUserEmailAndLoginMethod(String email, String loginmethod);
	
	User findByUserEmailAndUserPassword(String username, String password);

	User findByUserEmail(String username);
	
	User findByUserNickname(String usernickname);
	
	List<User> findByUserAuthority(String teacher);
	
	User findOneById(Integer id);
//	List<PtInfo> findByUserUserAuthority();	
}
