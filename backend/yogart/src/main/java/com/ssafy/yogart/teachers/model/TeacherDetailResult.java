package com.ssafy.yogart.teachers.model;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.yogart.user.model.User;

import lombok.Data;

@Data
public class TeacherDetailResult {
	private List<PtInfo> ptList;
	private User teacherInfo;
	public TeacherDetailResult() {
		ptList = new ArrayList<>(); 
		teacherInfo = new User();
	}
}
