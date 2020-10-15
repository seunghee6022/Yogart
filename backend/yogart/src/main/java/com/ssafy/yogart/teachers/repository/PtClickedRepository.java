package com.ssafy.yogart.teachers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.yogart.teachers.model.PtClicked;
import com.ssafy.yogart.teachers.model.PtInfo;
import com.ssafy.yogart.user.model.User;

public interface PtClickedRepository extends JpaRepository<PtClicked, Integer> {
	List<PtClicked> findByPtClickedName(PtInfo ptinfo);
	List<PtClicked> findByPtStudentId(User user);
	List<PtClicked> findAllByOrderByDateTimeDesc();
}
