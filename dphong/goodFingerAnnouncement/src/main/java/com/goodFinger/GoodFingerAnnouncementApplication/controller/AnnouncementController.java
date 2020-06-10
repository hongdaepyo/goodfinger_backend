package com.goodFinger.GoodFingerAnnouncementApplication.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goodFinger.GoodFingerAnnouncementApplication.service.AnnouncementServiceImpl;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
	private Logger logger = LogManager.getLogger(AnnouncementController.class);
	
	@Resource(name = "AnnouncementService")
	private AnnouncementServiceImpl announcementServiceImpl;
	
	@GetMapping("/hello")
	public String sayHello() {
		logger.debug("sayHello started");
		logger.debug("sayHello ended");
		return "helloWorld";
	}
	
	@GetMapping(path = "/getHello")
	public String helloSpringWorld() {
		logger.debug("helloSpringWorld started");
		logger.debug("helloSpringWorld ended");		
		return announcementServiceImpl.testFunc();
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