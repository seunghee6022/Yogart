package com.ssafy.yogart.teachers.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TeacherPTInfoResult {
	private PtInfo ptInfo;
	private List<Time> clicked;
	private List<LocalDateTime> soldOut;
	public TeacherPTInfoResult() {
		ptInfo = new PtInfo();
		clicked = new ArrayList<>();
		soldOut = new ArrayList<>();	
	}
}
