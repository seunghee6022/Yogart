package com.ssafy.yogart.mypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.yogart.mypage.model.GraphBodyPart;
import com.ssafy.yogart.user.model.User;

public interface GraphBodyPartRepository extends JpaRepository<GraphBodyPart, Integer> {
	List<GraphBodyPart> findByGraphBodyPartUserNickname(User user);
//	GraphBodyPart findByUserUserNickname(String nickname);
}
