package com.ssafy.yogart.teachers.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.yogart.teachers.model.PtInfo;
import com.ssafy.yogart.user.model.User;

public interface PtInfoRepository extends JpaRepository<PtInfo, Integer> {
	List<PtInfo> findByPtTeacherId(User user);
	Page<PtInfo> findAll(Pageable pageable);
	PtInfo findByPtId(Integer ptId);
	PtInfo findByPtName(String ptName);
}
