package com.sds.icto.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.icto.mysite.domain.GuestbookVo;
import com.sds.icto.mysite.repository.GuestbookDao;

@Service
public class GuestbookService {

	@Autowired
	GuestbookDao guestbookDao;

	public List<GuestbookVo> getAllList() {
		return guestbookDao.fetchList();
	}

	public void addGuestbook(GuestbookVo vo) {
		guestbookDao.insert(vo);
	}

	public boolean removeGuestbook(int no, String password) {
		return guestbookDao.delete(no, password);
	}
}
