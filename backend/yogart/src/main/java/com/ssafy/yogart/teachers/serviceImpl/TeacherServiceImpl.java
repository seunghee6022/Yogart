package com.ssafy.yogart.teachers.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.yogart.teachers.model.PtClicked;
import com.ssafy.yogart.teachers.model.PtInfo;
import com.ssafy.yogart.teachers.repository.PtClickedRepository;
import com.ssafy.yogart.teachers.repository.PtInfoRepository;
import com.ssafy.yogart.teachers.service.TeacherService;
import com.ssafy.yogart.user.model.User;
import com.ssafy.yogart.user.repository.UserRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	PtInfoRepository ptInfoRepository;
	
	@Autowired
	PtClickedRepository ptClickedRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<PtInfo> showAllTeacherPTlist(User user) {
		return ptInfoRepository.findByPtTeacherId(user);
	}

	@Override
	public Page<PtInfo> showAllClassList(int limit) {
		Pageable page = PageRequest.of((limit-1)/10, 10);
		return ptInfoRepository.findAll(page);
	}
	
	@Override
	public PtInfo showPTInfo(int ptId) {
		return ptInfoRepository.findByPtId(ptId);
	}

	@Override
	public PtInfo showPTInfoByName(String name) {
		return ptInfoRepository.findByPtName(name);
	}
	
	@Override
	public PtInfo updatePtInfo(PtInfo ptinfo) {
		return ptInfoRepository.save(ptinfo);
	}
	
	@Override
	public List<PtClicked> showClassTime(PtInfo ptinfo) {
		return ptClickedRepository.findByPtClickedName(ptinfo);
	}
	
	@Override
    public PtClicked updatePtClickedInfo(PtClicked ptClicked) {
        return ptClickedRepository.save(ptClicked);
    }

	@Override
	public List<PtClicked> showOrderByTimeDESC() {
		return ptClickedRepository.findAllByOrderByDateTimeDesc();
	}

	@Override
	public List<PtClicked> showAllClass() {
		return ptClickedRepository.findAll();
	}



}
