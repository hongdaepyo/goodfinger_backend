package com.goodFinger.GoodFingerAnnouncementApplication.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goodFinger.GoodFingerAnnouncementApplication.document.Announcement;
import com.goodFinger.GoodFingerAnnouncementApplication.document.EtcOption;
import com.goodFinger.GoodFingerAnnouncementApplication.document.PartTimeInfo;
import com.goodFinger.GoodFingerAnnouncementApplication.document.Question;
import com.goodFinger.GoodFingerAnnouncementApplication.service.AnnouncementServiceImpl;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
	private Logger logger = LogManager.getLogger(AnnouncementController.class);
	
	@Resource(name = "AnnouncementService")
	private AnnouncementServiceImpl announcementServiceImpl;
	
	@GetMapping(path = "/hello")
	public String sayHello() {
		logger.debug("sayHello started");
		logger.debug("sayHello ended");
		return "helloWorld";
	}
	
	@GetMapping(path = "/list")
	public List<Announcement> getAnnouncementList() {
		logger.debug("getAnnouncementList started");
		logger.debug("getAnnouncementList ended");		
		return announcementServiceImpl.getAnnouncementList();
	}
	
	@GetMapping(path = "/list/wait")
	public List<Announcement> getWaitingAnnouncementList() throws Exception {
		logger.debug("getWaitingAnnouncementList started");
		logger.debug("getWaitingAnnouncementList ended");		
		return announcementServiceImpl.getWaitingAnnouncementList();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, value = "/insert")
	public String insertAnnouncement(@RequestBody JSONObject requestBody) {
		logger.debug("insertAnnouncement started");
		String result = "";
		
		Map<String, Object> parttime = new LinkedHashMap<String, Object>();
		parttime = (Map<String, Object>) requestBody.get("parttime");
		String flag = (String) parttime.get("flag");
		String company = (String) parttime.get("company");
		String[] category = (String[]) parttime.get("category");
		int recruitment = (int) parttime.get("recruitment");
		String preferredSex = (String) parttime.get("preferredSex");
		int[] preferredAge = (int[]) parttime.get("preferredAge");
		String task = (String) parttime.get("task");
		String startDate = (String) parttime.get("startDate");
		String endDate = (String) parttime.get("endDate");
		String startTime = (String) parttime.get("startTime");
		String endTime = (String) parttime.get("endTime");
		String[] salary = (String[]) parttime.get("salary");
		EtcOption etc = (EtcOption) parttime.get("etc");
		PartTimeInfo partTimeInfo = (PartTimeInfo) parttime.get("partTimeInfo");
		
		Question question = (Question) requestBody.get("question");
		
		Announcement announcement = new Announcement();
		announcement.setFlag(flag);
		announcement.setCompany(company);
		announcement.setCategory(category);
		announcement.setRecruitment(recruitment);
		announcement.setPreferredSex(preferredSex);
		announcement.setPreferredAge(preferredAge);
		announcement.setStartDate(startDate);
		announcement.setEndDate(endDate);
		announcement.setStartTime(startTime);
		announcement.setEndTime(endTime);
		announcement.setSalary(salary);
		announcement.setEtc(etc);
		announcement.setPartTimeInfo(partTimeInfo);
		
		try {
			result = announcementServiceImpl.insertAnnouncement(announcement);
			logger.debug("result = " + announcement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("insertAnnouncement ended");
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/insertTestData")
	public String setTestData() {
		logger.debug("setTestData started");
		String result = "";
		
		try {
			result = announcementServiceImpl.insertTestData();
			logger.debug("result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("setTestData ended");
		return result;
	}
}