package com.goodFinger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.goodFinger.Repo.AnnouncementRepo;
import com.goodFinger.document.Announcement;

public class AnnouncementServiceImpl {
	
	@Autowired
	private AnnouncementRepo announcementRepo;
	
	private MongoTemplate mongoTemplate;

	public String testFunc() {
		Announcement announcement = (Announcement) announcementRepo.findById("announcement1");
		
		System.out.println(announcement.toString());
		
		return announcement.toString();
	}
	
}