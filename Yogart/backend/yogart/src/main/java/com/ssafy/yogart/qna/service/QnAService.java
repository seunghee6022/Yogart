package com.ssafy.yogart.qna.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ssafy.yogart.qna.model.QnA;
import com.ssafy.yogart.qna.model.QnAReply;

public interface QnAService {
	// 전체목록 조회
	Page<QnA> allQnA(int page);	
	// 댓글 조희
	List<QnAReply> allQnAReply(int qnaId);
	// 상세 조회
	QnA detailQnA(int qnaNo);
	// 질문 등록
	QnA createQnA(QnA qna);
	// 댓글 등록
	QnAReply createReplyQnA(QnAReply qnaReply);
	// 질문 수정
	QnA updateQnA(QnA qna);
	// 질문 삭제
	boolean deleteQnA(int qnaNo);
}
