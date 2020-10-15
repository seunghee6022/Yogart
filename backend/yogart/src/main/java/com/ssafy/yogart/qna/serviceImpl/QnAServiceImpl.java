package com.ssafy.yogart.qna.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ssafy.yogart.qna.model.QnA;
import com.ssafy.yogart.qna.model.QnAReply;
import com.ssafy.yogart.qna.repository.QnAReplyRepository;
import com.ssafy.yogart.qna.repository.QnARepository;
import com.ssafy.yogart.qna.service.QnAService;

@Service
public class QnAServiceImpl implements QnAService{
	@Autowired
	private QnARepository qnaRepository;
	
	@Autowired
	private QnAReplyRepository qnaReplyRepository;
	
	@Autowired
    public QnAServiceImpl(QnARepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }
	
	@Override
	public Page<QnA> allQnA(int page) {
		return qnaRepository.findAll(PageRequest.of(page-1, 5, Sort.by("createDate").descending()));
	}
	
	@Override
	public List<QnAReply> allQnAReply(int qnaId) {
		return qnaReplyRepository.findByReplyQnaId(qnaId);
	}

	@Override
	public QnA detailQnA(int id) {
		return qnaRepository.findByQnaId(id);
	}

	@Override
	public QnA createQnA(QnA qna) {
		return qnaRepository.save(qna);
	}
	
	@Override
	public QnAReply createReplyQnA(QnAReply qnaReply) {
		return qnaReplyRepository.save(qnaReply);
	}

	@Override
	public QnA updateQnA(QnA qna) {
		return qnaRepository.save(qna);
	}

	@Override
	public boolean deleteQnA(int id) {
		QnA qna = qnaRepository.findByQnaId(id);
		if(qna != null) {
			qnaRepository.delete(qna);
			return true;
		}
		return false;
	}
}
