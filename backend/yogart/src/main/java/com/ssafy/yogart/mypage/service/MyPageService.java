package com.ssafy.yogart.mypage.service;

import java.util.List;

import com.ssafy.yogart.mypage.model.GraphBodyPart;
import com.ssafy.yogart.mypage.model.GraphTime;
import com.ssafy.yogart.teachers.model.PtClicked;
import com.ssafy.yogart.user.model.User;

public interface MyPageService {
	List<GraphBodyPart> showTagGraph(User user);
	List<GraphTime> showattendance(User user);
	List<PtClicked> showPTList(User user);
	GraphBodyPart saveTag(GraphBodyPart bodypart);
	GraphTime saveTime(GraphTime graphtime);
}
