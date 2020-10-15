package com.ssafy.yogart.mypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.yogart.mypage.model.GraphTime;
import com.ssafy.yogart.user.model.User;

public interface GraphTimeRepository extends JpaRepository<GraphTime, Integer> {
	List<GraphTime> findByGraphUserNickname(User user);
}
