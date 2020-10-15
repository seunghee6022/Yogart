package com.ssafy.yogart.mypage.model;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.yogart.teachers.model.PtClicked;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class MyPagePtResult {
	private List<PtClicked> pastCourses;
	private List<PtClicked> todayCourses;
	private List<PtClicked> futureCourses;
	
	public MyPagePtResult() {
		this.pastCourses = new ArrayList<>();
		this.todayCourses = new ArrayList<>();
		this.futureCourses = new ArrayList<>();
	}
}
