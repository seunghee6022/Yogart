package com.ssafy.yogart.notice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.yogart.notice.model.Notice;
import com.ssafy.yogart.notice.service.NoticeService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api/notice")
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private NoticeService noticeService;

    @ApiOperation(value = "모든 공지사항 정보를 반환한다.", response = Page.class)
	@GetMapping(value="/list/{page}")
	public ResponseEntity<Page<Notice>> allNotice(@PathVariable int page) {
		logger.debug("allNotice - 호출");
		return new ResponseEntity<Page<Notice>>(noticeService.allNotice(page), HttpStatus.OK);
	}
    
    @ApiOperation(value = "글번호에 해당하는 공지사항글의 정보를 반환한다.", response = Notice.class)    
	@GetMapping(value="/{id}")
	public ResponseEntity<Notice> detailNotice(@PathVariable int id) {
		logger.debug("detailNotice - 호출");
		return new ResponseEntity<Notice>(noticeService.detailNotice(id), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 공지사항을 등록한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping(value="/create")
	public ResponseEntity<String> createNotice(@RequestBody Notice notice) {
		logger.debug("createNotice - 호출");
		if (noticeService.createNotice(notice) != null) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "글번호에 해당하는 공지사항의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateNotice(@RequestBody Notice notice) {
		logger.debug("updateNotice - 호출");
		logger.debug("" + notice);
		
		if (noticeService.updateNotice(notice) != null) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 공지사항의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBoard(@PathVariable int id) {
		logger.debug("deleteNotice - 호출");
		if (noticeService.deleteNotice(id)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}
