package com.ssafy.yogart.notice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ssafy.yogart.notice.model.Notice;
import com.ssafy.yogart.notice.repository.NoticeRepository;
import com.ssafy.yogart.notice.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

	@Override
	public Page<Notice> allNotice(int page) {
		return noticeRepository.findAll(PageRequest.of(page - 1, 5, Sort.by("createDate").descending()));
	}

	@Override
	public Notice detailNotice(int id) {
		return noticeRepository.findByNoticeId(id);
	}

	@Override
	public Notice createNotice(Notice notice) {
		return noticeRepository.save(notice);
	}

	@Override
	public Notice updateNotice(Notice notice) {
		return noticeRepository.save(notice);
	}

	@Override
	public boolean deleteNotice(int id) {
		Notice notice = noticeRepository.findByNoticeId(id);
		if(notice != null) {
			noticeRepository.delete(notice);
			return true;
		}
		return false;
	}
}
