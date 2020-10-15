package com.ssafy.yogart.notice.service;

import org.springframework.data.domain.Page;

import com.ssafy.yogart.notice.model.Notice;

public interface NoticeService {
	// 전체목록 조회

	Page<Notice> allNotice(int page);
	// 상세 조회
	Notice detailNotice(int id);
	// 공지사항 등록
	Notice createNotice(Notice notice);
	// 공지사항 수정
	Notice updateNotice(Notice notice);
	// 공지사항 삭제
	boolean deleteNotice(int id);
}
