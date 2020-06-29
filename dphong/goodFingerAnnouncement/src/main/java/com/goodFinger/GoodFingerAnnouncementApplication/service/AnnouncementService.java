package com.goodFinger.GoodFingerAnnouncementApplication.service;

import java.util.List;

import com.goodFinger.GoodFingerAnnouncementApplication.document.Announcement;

public interface AnnouncementService {
	
	public List<Announcement> getAnnouncementList() throws Exception;
	public String insertTestData() throws Exception;
	
}