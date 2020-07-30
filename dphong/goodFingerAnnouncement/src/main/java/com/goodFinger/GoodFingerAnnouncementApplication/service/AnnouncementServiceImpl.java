package com.goodFinger.GoodFingerAnnouncementApplication.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goodFinger.GoodFingerAnnouncementApplication.Repo.AnnouncementRepo;
import com.goodFinger.GoodFingerAnnouncementApplication.controller.AnnouncementController;
import com.goodFinger.GoodFingerAnnouncementApplication.document.Announcement;

@Component("AnnouncementService")
public class AnnouncementServiceImpl implements AnnouncementService {
	private Logger logger = LogManager.getLogger(AnnouncementServiceImpl.class);
	
	@Autowired
	private AnnouncementRepo announcementRepo;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Announcement> getAnnouncementList() {
		
		return announcementRepo.findAll();
	}
	
	@Override
	public List<Announcement> getWaitingAnnouncementList() throws Exception {
		logger.debug("getWaitingAnnouncementList service started");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		
		String todayDate = format.format(time);
		
		logger.debug("getWaitingAnnouncementList service ended");
		return announcementRepo.findAllByStartDateAfter(todayDate);
	}
	
	@Override
	public Announcement insertAnnouncement(Announcement announcement) throws Exception {
		logger.debug("insertAnnouncement service started");
		
		Announcement result = mongoTemplate.insert(announcement);
		
		logger.debug("insertAnnouncement service ended");
		return result;
	}

	@Override
	public String insertTestData() throws Exception {
		Announcement announcement = new Announcement();
		announcement.setAnnouncementId("testId");
		announcement.setApplicant(new String[] {"user1", "user2", "user3"});
		announcement.setApplicant_questions(null);
		announcement.setCategory(new String[]{"업종1", "업종2"});
		announcement.setFlag("Y");
		announcement.setCompany("goodfingerCom");
		announcement.setLocationCity("seoul");
		announcement.setLocationDistrict("dongjack");
		announcement.setRecruitment(5);
		announcement.setPreferredSex("all");
		announcement.setPreferredAge(new int[] {10, 20 ,30});
		announcement.setTask("매장관리");
		announcement.setStartDate("2019-11-20");
		announcement.setEndDate("2019-12-20");
		announcement.setStartTime("00:00");
		announcement.setEndTime("24:00");
		announcement.setSalary(new String[] {"day", "time"});
		announcement.setEtc(null);
		announcement.setJoboffer("{간단소개:\"솔루션회사입니다. 그룹웨어\", picture:[\"경로1\",\"경로2\",\"경로3\"]}");
		announcement.setMemo("메모");
		announcement.setQuestions("question array");
		
		mongoTemplate.insert(announcement);
		
		return "TRUE";
	}

}