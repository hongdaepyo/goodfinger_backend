package com.goodFinger.GoodFingerAnnouncementApplication.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
	
	@RequestMapping(method = RequestMethod.POST, value = "/insert")
	public String insertAnnouncement(@RequestBody JSONObject requestBody) {
		logger.debug("insertAnnouncement started");
		String result = "";
		
		JSONObject partTimeObj = getJSONObject(requestBody, "parttime");
		
		Announcement announcement = JsonToObject(partTimeObj, Announcement.class);
		
		try {
			result = announcementServiceImpl.insertAnnouncement(announcement);
			logger.debug("result = " + result);
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
	
	@SuppressWarnings("unchecked")
	private JSONObject getJSONObject(JSONObject jsonObj, String key) {
		return new JSONObject((Map<String, Object>) jsonObj.get(key));
	}
	
	private <T> T JsonToObject(JSONObject json, Class<T> targetClass) {
		return new Gson().fromJson(json.toJSONString(), (Type) targetClass);
	}
}