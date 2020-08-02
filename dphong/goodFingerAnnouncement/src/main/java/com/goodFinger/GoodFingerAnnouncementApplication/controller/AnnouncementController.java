package com.goodFinger.GoodFingerAnnouncementApplication.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goodFinger.GoodFingerAnnouncementApplication.document.Announcement;
import com.goodFinger.GoodFingerAnnouncementApplication.document.ApplicantQ;
import com.goodFinger.GoodFingerAnnouncementApplication.document.ApplicantQuestion;
import com.goodFinger.GoodFingerAnnouncementApplication.document.EtcOption;
import com.goodFinger.GoodFingerAnnouncementApplication.document.PartTimeInfo;
import com.goodFinger.GoodFingerAnnouncementApplication.document.Question;
import com.goodFinger.GoodFingerAnnouncementApplication.service.AnnouncementServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.minidev.json.JSONArray;
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
		JSONArray question = getJSONArray(requestBody, "question");
		
		Announcement announcement = JsonToObject(partTimeObj, Announcement.class);
		List<ApplicantQ> applicantQList = JsonArrayToObject(question, ApplicantQ.class);
		
		// TODO applicant question은 더 처리해야함.
		
		try {
			Announcement resultObj = announcementServiceImpl.insertAnnouncement(announcement);
			
			if (resultObj != null) {
				ApplicantQuestion applicantQuestion = new ApplicantQuestion();
				applicantQuestion.setAnnouncementId(resultObj.getId());
				applicantQuestion.setApplicantQList(applicantQList.toArray(new ApplicantQ[applicantQList.size()]));
				
				logger.debug("result = " + resultObj);
				result = "TRUE";
			} else {
				logger.debug("insertAnnouncement failed");
				result = "FALSE";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("insertAnnouncement ended");
		return result;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/delete/{id}")
	public String deleteAnnouncement(@PathVariable String id) {
		logger.debug("deleteAnnouncement started");
		String result = "";
		
		logger.debug("delete id = " + id);
		try {
			result = announcementServiceImpl.deleteAnnouncement(id);
			
			// TODO 파일 삭제 기능 추가할 것.
			logger.debug("result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("deleteAnnouncement ended");
		return result;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/deleteAll")
	public String deleteAllAnnouncement() {
		logger.debug("deleteAllAnnouncement started");
		String result = "";
		
		try {
			result = announcementServiceImpl.deleteAllAnnouncement();
			logger.debug("result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("deleteAllAnnouncement ended");
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
	
	@SuppressWarnings("unchecked")
	private JSONArray getJSONArray(JSONObject jsonObj, String key) {
		JSONArray jsonArray = new JSONArray();
		ArrayList<JSONObject> jsonObjList = (ArrayList<JSONObject>) jsonObj.get(key);
		for (int i = 0; i < jsonObjList.size(); i++) {
			jsonArray.add(jsonObjList.get(i));
		}
		
		return jsonArray;
	}
	
	private <T> T JsonToObject(JSONObject json, Class<T> targetClass) {
		return new Gson().fromJson(json.toJSONString(), (Type) targetClass);
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> JsonArrayToObject(JSONArray jsonArray, Class<T> targetClass) {
		List<T> list = new ArrayList<T>();
		JSONObject jsonObj;
		Gson gson = new Gson();
		
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObj = new JSONObject((Map<String, Object>) jsonArray.get(i));
			T targetObject = gson.fromJson(jsonObj.toJSONString(), (Type) targetClass);
			list.add(targetObject);
		}
		
		return list;
	}
}