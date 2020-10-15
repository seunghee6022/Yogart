package com.ssafy.yogart.aicoach.service;

import java.util.List;

import com.ssafy.yogart.aicoach.model.AicoachCourse;
import com.ssafy.yogart.user.model.User;

public interface AicoachService {
	// 전체목록 조회
	List<AicoachCourse> allCourse();
	// 유저별 코스 리스트 조회
	List<AicoachCourse> userCourse(String nickname);
	// 상세 조회
	AicoachCourse detailCourse(int aiCourseId);
	// 코스 등록
	AicoachCourse createCourse(AicoachCourse aicoachCourse);
}
