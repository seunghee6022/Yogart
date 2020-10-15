package com.ssafy.yogart.qna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.yogart.qna.model.QnAReply;

@Transactional
public interface QnAReplyRepository extends JpaRepository<QnAReply, Integer> {
	@Query(value="SELECT * FROM qna_reply WHERE qna_reply_qna_id = :qna_reply_qna_id", nativeQuery=true)
	List<QnAReply> findByReplyQnaId(@Param("qna_reply_qna_id") int qna_reply_qna_id);
}
