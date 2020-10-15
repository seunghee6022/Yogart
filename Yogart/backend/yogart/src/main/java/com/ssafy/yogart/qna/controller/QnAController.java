package com.ssafy.yogart.qna.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.yogart.qna.model.QnA;
import com.ssafy.yogart.qna.model.QnAReply;
import com.ssafy.yogart.qna.model.QnAResult;
import com.ssafy.yogart.qna.service.QnAService;
import com.ssafy.yogart.user.model.User;
import com.ssafy.yogart.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/qna")
public class QnAController {

	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private QnAService qnaService;
	
	@Autowired
	private UserService userService;

    @ApiOperation(value = "모든 질문게시판 정보를 반환한다.", response = List.class)
	@GetMapping(value="/list/{page}")
	public ResponseEntity<Page<QnA>> allQna(@PathVariable int page) throws Exception {
		logger.debug("retrieveQnA - 호출");
		return new ResponseEntity<Page<QnA>>(qnaService.allQnA(page), HttpStatus.OK);
	}
    
    @ApiOperation(value = "모든 질문게시판답글 정보를 반환한다.", response = List.class)
   	@GetMapping(value="/reply/list/{qnaId}")
   	public ResponseEntity<QnAResult> allQnaReply( @PathVariable int qnaId) throws Exception {
   		logger.debug("retrieveQnAReply - 호출");
   		System.out.println(qnaId);
//   		if(qnaService.allQnAReply(qnaId) == null) {
//   			return new ResponseEntity<List<QnAReply>>(" ", HttpStatus.NO_CONTENT);
//   		}
   		QnAResult qnaResult = new QnAResult();
   		qnaResult.setList(qnaService.allQnAReply(qnaId));
   		qnaResult.setAdmin(true);
//   		System.out.println(qnaResult.getList().get(0).getUserEmail());
   		return new ResponseEntity<QnAResult>(qnaResult, HttpStatus.OK);
   	}
    
    @ApiOperation(value = "글번호에 해당하는 질문글의 정보를 반환한다.", response = QnA.class)    
	@GetMapping("/{id}")
	public ResponseEntity<QnA> detailQna(@PathVariable int id) {
		logger.debug("detailQnA - 호출");
		return new ResponseEntity<QnA>(qnaService.detailQnA(id), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 QnA를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping(value="/make", produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> createQna(@RequestHeader(value="config") Map<String, Object> header, @RequestBody Map<String, Object> content) {
		logger.debug("createQnA - 호출");
		System.out.println((String)header.get("authorization"));
		User user = userService.authentication((String)header.get("authorization"));
		System.out.println(user.getUserName());
		QnA qna = new QnA();
		qna.setQnaTitle((String)content.get("qnaTitle"));
		qna.setUserEmail(user);
		qna.setQnaContent((String)content.get("qnaContent"));
		if (qnaService.createQnA(qna) != null) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    
    @ApiOperation(value = "새로운 QnA reply를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping(value="/reply/make", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> createQnaReply(@RequestHeader(value="config") Map<String, Object> header, @RequestBody Map<String, Object> content) {
     System.out.println("success::");
       logger.debug("createQnA - 호출");
       System.out.println((String)header.get("authorization"));
       System.out.println(content);
       User user = userService.authentication((String)header.get("authorization"));
       if(user.getUserAuthority().equals("ADMIN")) {
          QnAReply qnaReply = new QnAReply();
          QnA qna = qnaService.detailQnA((Integer)content.get("qnaId"));
          qnaReply.setQnaId(qna);
             qnaReply.setUserEmail(user);
             qnaReply.setQnaReplyContent((String)content.get("ReplyContent"));
             if (qnaService.createReplyQnA(qnaReply) != null) {
                return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
             }
       }
       return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
    
    @ApiOperation(value = "글번호에 해당하는 QnA의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateQna(@RequestBody QnA qna) {
		logger.debug("updateQnA - 호출");
		logger.debug("" + qna);
		
		if (qnaService.updateQnA(qna) != null) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 QnA의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteQna(@PathVariable int id) {
		logger.debug("deleteQna - 호출");
		if (qnaService.deleteQnA(id)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}