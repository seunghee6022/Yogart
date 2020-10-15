package com.ssafy.yogart.teachers.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ssafy.yogart.teachers.model.PtClicked;
import com.ssafy.yogart.teachers.model.PtInfo;
import com.ssafy.yogart.user.model.User;

public interface TeacherService {
	
	List<PtInfo> showAllTeacherPTlist(User user);
	
	Page<PtInfo> showAllClassList(int limit);
	List<PtClicked> showAllClass();
	
	PtInfo showPTInfo(int ptId);
	
	PtInfo showPTInfoByName(String name);
	
	PtInfo updatePtInfo(PtInfo ptinfo);
	
	List<PtClicked> showClassTime(PtInfo ptinfo);
	
	PtClicked updatePtClickedInfo(PtClicked ptClicked);
	
	List<PtClicked> showOrderByTimeDESC();
}
