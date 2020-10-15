package com.ssafy.yogart.teachers.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.yogart.aicoach.controller.AicoachController;
import com.ssafy.yogart.aicoach.model.AicoachCourse;
import com.ssafy.yogart.teachers.model.PtClicked;
import com.ssafy.yogart.teachers.model.PtInfo;
import com.ssafy.yogart.teachers.model.TeacherDetailResult;
import com.ssafy.yogart.teachers.model.TeacherPTInfoResult;
import com.ssafy.yogart.teachers.model.Time;
import com.ssafy.yogart.teachers.service.TeacherService;
import com.ssafy.yogart.user.model.User;
import com.ssafy.yogart.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	private static final Logger logger = LoggerFactory.getLogger(AicoachController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private UserService userService;
	
	
	@ApiOperation(value = "강사리스트를 출력", response = List.class)
	@GetMapping(value="/list")
	public ResponseEntity<List<User>> teacherList() throws Exception {
		List<User> teachers = userService.showAllTeacherlist();
		return new ResponseEntity<List<User>>(teachers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "강사가 가능한 시간들를 출력", response = List.class)
	@GetMapping(value="/list/detail/{id}")
	public ResponseEntity<TeacherDetailResult> teacherPTList(@PathVariable int id) throws Exception {
		System.out.println("id:" +  id);
		User teacher = userService.findUser(id);
		System.out.println(teacher.getUserName());
		List<PtInfo> teachersPT = teacherService.showAllTeacherPTlist(teacher);
		System.out.println("Teacher's PT Class: " + teachersPT);
		TeacherDetailResult result = new TeacherDetailResult();
		result.setPtList(teachersPT);
		result.setTeacherInfo(teacher);
		return new ResponseEntity<TeacherDetailResult>(result , HttpStatus.OK);
	}
	
	@ApiOperation(value = "강사의 수업 정보와 이미 예약된 PT리스트를 가져온다.", response = TeacherPTInfoResult.class)
	@GetMapping(value="/pt/{ptId}")
	public ResponseEntity<TeacherPTInfoResult> teacherReservedPTList(@PathVariable int ptId) throws Exception {
		System.out.println("ptId:" +  ptId);
		PtInfo ptInfo = teacherService.showPTInfo(ptId);
		List<PtClicked> clickedList = teacherService.showClassTime(ptInfo);
		List<Time> timeList = new ArrayList<>(); 
		List<LocalDateTime> soldOut = new ArrayList<>();
		for(int i = 0; i < clickedList.size(); i++) {
			PtClicked temp = clickedList.get(i);
			Time time = new Time(temp.getPtDay(), temp.getPtTime());
			if(temp.getDateTime() == null) {
				timeList.add(time);
				System.out.println(temp.getPtClickedId());
				continue;
			}
			soldOut.add(temp.getDateTime());
		}
		TeacherPTInfoResult result = new TeacherPTInfoResult();
		result.setClicked(timeList);
		result.setPtInfo(ptInfo);
		result.setSoldOut(soldOut);
		return new ResponseEntity<TeacherPTInfoResult>(result , HttpStatus.OK);
	}
	
	// 서비스에 맞게 데이터 처리하는지 확인하고 만들 것
	@ApiOperation(value = "신청완료시 수업에 대한 정보와 유저정보 가져온다.", response = String.class)
	@PostMapping(value="/pt-regist")
	public ResponseEntity<String> teacherReserved(@RequestHeader Map<String,String> header, @RequestBody Map<String, Object> data) throws Exception {
		String token = header.get("authorization");
		User user = userService.authentication(token);
		System.out.println(user.getUserNickname());
		Map<String,Object> ptInfo = (Map<String, Object>) data.get("ptInfo");
		Integer day = (Integer)data.get("day");
		String temp = (String)data.get("time");
		String time = temp.substring(0, 23);
		LocalDateTime timeSet = LocalDateTime.parse(time);
		timeSet = timeSet.plusHours(9);
		System.out.println(timeSet);
		int minusSpoon = (int)ptInfo.get("ptPrice");
		if(user.getUserSpoon() < minusSpoon) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
		System.out.println("-" + minusSpoon + " 스푼 차감되었습니다.");
		user.setUserSpoon(user.getUserSpoon() -  minusSpoon);
		System.out.println("잔여 스푼: " + user.getUserSpoon());
		userService.updateInfo(user);
		Map<String,Object> t = (Map<String, Object>) ptInfo.get("ptTeacher"); 
		int teacherId = (Integer)t.get("id");
		User teacher = userService.findUser(teacherId);
		System.out.println(teacher);

		PtInfo ptinfo = new PtInfo((Integer)ptInfo.get("ptId"),
									(String)ptInfo.get("ptName"),
									(Integer)ptInfo.get("ptPrice"),
									(String)ptInfo.get("ptIntro"),
									teacher);
		List<PtClicked> clickedList = teacherService.showClassTime(ptinfo);
		PtClicked click2 = null; PtClicked click = null;
		go:
		for(int i = 0; i < clickedList.size(); i++) {
			click2 = clickedList.get(i);
			if(click2.getDateTime() == null) {
				if((day == click2.getPtDay()) && (timeSet.getHour() == click2.getPtTime())) {
					click = click2;
					break go;
				}
			}
		}
		click.setDateTime(timeSet);
		click.setPtStudentId(user);
		click.setIsAttend(false);
		teacherService.updatePtClickedInfo(click);
		PtClicked clickNull = new PtClicked(click.getPtDay(),click.getPtTime(),userService.findUser(7),
				false,null,click.getPtClickedName());
		teacherService.updatePtClickedInfo(clickNull);
		return new ResponseEntity<String>(SUCCESS , HttpStatus.OK);
	}
	
	@ApiOperation(value = "강사의 수업개설", response = String.class)
	@PostMapping(value="/pt-create")
	public ResponseEntity<String> teacherClassCreate(@RequestHeader Map<String,String> header, @RequestBody Map<String, Object> data) throws Exception {
		String token = header.get("authorization");
		User user = userService.authentication(token);
		List clickedList = (ArrayList) data.get("clicked");
		String ptName = (String)data.get("ptName");
		String ptIntro = (String)data.get("ptIntro");
		int ptPrice = Integer.parseInt((String)data.get("ptPrice"));
		System.out.println(clickedList); // 원하는 시간대 받기 완료
		System.out.println(ptName); // 피티명 받기 완료 	
		System.out.println(ptIntro);
		System.out.println(ptPrice); // 가격 받기 완료
		PtInfo ptinfo = new PtInfo();
		ptinfo.setPtName(ptName);
		ptinfo.setPtPrice(ptPrice);
		ptinfo.setPtIntro(ptIntro);
		ptinfo.setPtTeacherId(user);
		PtInfo ptInfo = teacherService.updatePtInfo(ptinfo);
		for(int i = 0; i < clickedList.size(); i++) {
			Map<String,Integer> click = (Map<String, Integer>) clickedList.get(i);
			PtClicked clickNull = new PtClicked(click.get("day"),click.get("time"),
					userService.findUser(7), // default 계정이 7번자리에 필요 -> 나중에 1번자리로 바꾸기
					false,null,ptInfo);
			teacherService.updatePtClickedInfo(clickNull);
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "요가 수업 리스트 인피니트 핸들러", response = List.class)
	@GetMapping(value="/class/list/{limit}")
	public ResponseEntity<Page<PtInfo>> teacherClassList(@PathVariable int limit) throws Exception {
		Page<PtInfo> allList = teacherService.showAllClassList(limit);
		return new ResponseEntity<Page<PtInfo>>(allList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "요가 수업 1:1 PT 방 개설", response = PtClicked.class)
	@GetMapping(value="/pt-now")
	public ResponseEntity<PtClicked> TeacherPTroomOpen(@RequestHeader Map<String,String> header) {
    	String token = header.get("authorization");
		User user = userService.authentication(token);
		List<PtClicked> timeList = teacherService.showOrderByTimeDESC();
		PtClicked ptOpen = null;
		for(PtClicked pt : timeList) {
			String temp = pt.getPtClickedName().getPtTeacherId().getUserNickname();
			if(temp.equals(user.getUserNickname())) {
				ptOpen = pt;
				break;
			}
		}
		if(ptOpen == null) return new ResponseEntity<PtClicked>(ptOpen, HttpStatus.NO_CONTENT);
		return new ResponseEntity<PtClicked>(ptOpen, HttpStatus.OK);
	}
	
	@ApiOperation(value = "PT 리스트")
	@GetMapping(value="/pt-list")
	public ResponseEntity<List<PtInfo>> TeacherPTList(@RequestHeader Map<String,String> header) {
    	String token = header.get("authorization");
		User user = userService.authentication(token);
		List<PtInfo> list = teacherService.showAllTeacherPTlist(user);
		return new ResponseEntity<List<PtInfo>>(list, HttpStatus.OK);
	}
	
}
