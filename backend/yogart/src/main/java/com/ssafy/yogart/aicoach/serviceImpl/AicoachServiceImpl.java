package com.ssafy.yogart.aicoach.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.yogart.aicoach.model.AicoachCourse;
import com.ssafy.yogart.aicoach.repository.AicoachCourseRepository;
import com.ssafy.yogart.aicoach.service.AicoachService;
import com.ssafy.yogart.user.model.User;

@Service
public class AicoachServiceImpl implements AicoachService {
	
	@Autowired
	private AicoachCourseRepository aicoachcourseRepository;


	@Override
	public List<AicoachCourse> allCourse() {
		List<AicoachCourse> list = aicoachcourseRepository.findAll();
		return list;
	}
	
	@Override
	public List<AicoachCourse> userCourse(String nickname) {
		List<AicoachCourse> list = aicoachcourseRepository.findByAiUserNickname(nickname);
		return list;
	}
	

	@Override
	public AicoachCourse detailCourse(int aiCourseId) {
		AicoachCourse aicoach = aicoachcourseRepository.findByAiCourseId(aiCourseId);
		return aicoach;
	}

	@Override
	public AicoachCourse createCourse(AicoachCourse aicoachCourse) {
		return aicoachcourseRepository.save(aicoachCourse);
	}

}
