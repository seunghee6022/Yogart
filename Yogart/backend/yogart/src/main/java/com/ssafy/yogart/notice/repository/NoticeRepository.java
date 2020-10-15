package com.ssafy.yogart.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.yogart.notice.model.Notice;

@Repository
@Transactional
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	Notice findByNoticeId(Integer noticeid);
}
