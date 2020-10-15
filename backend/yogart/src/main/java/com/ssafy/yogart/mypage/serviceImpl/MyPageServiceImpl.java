package com.ssafy.yogart.mypage.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.yogart.mypage.model.GraphBodyPart;
import com.ssafy.yogart.mypage.model.GraphTime;
import com.ssafy.yogart.mypage.repository.GraphBodyPartRepository;
import com.ssafy.yogart.mypage.repository.GraphTimeRepository;
import com.ssafy.yogart.mypage.service.MyPageService;
import com.ssafy.yogart.teachers.model.PtClicked;
import com.ssafy.yogart.teachers.repository.PtClickedRepository;
import com.ssafy.yogart.teachers.repository.PtInfoRepository;
import com.ssafy.yogart.user.model.User;

@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	private GraphBodyPartRepository graphBodyPartRepository;
	
	@Autowired
	private GraphTimeRepository graphTimeRepository;
	
	@Autowired
	private PtClickedRepository ptClickedRepository;

	@Autowired
	private PtInfoRepository ptInfoRepository;
	
	@Override
	public List<GraphBodyPart> showTagGraph(User user) {
		return graphBodyPartRepository.findByGraphBodyPartUserNickname(user);
	}

	@Override
	public List<GraphTime> showattendance(User user) {
		return graphTimeRepository.findByGraphUserNickname(user);
	}

	@Override
	public List<PtClicked> showPTList(User user) {
		return ptClickedRepository.findByPtStudentId(user);
	}

	@Override
	public GraphBodyPart saveTag(GraphBodyPart bodypart) {
		return graphBodyPartRepository.save(bodypart);
	}

	@Override
	public GraphTime saveTime(GraphTime graphtime) {
		return graphTimeRepository.save(graphtime);
	}
	
}
