package com.ssafy.yogart.qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.yogart.qna.model.QnA;

@Transactional
public interface QnARepository extends JpaRepository<QnA, Integer> {
	QnA findByQnaId(Integer qnaid);
}
