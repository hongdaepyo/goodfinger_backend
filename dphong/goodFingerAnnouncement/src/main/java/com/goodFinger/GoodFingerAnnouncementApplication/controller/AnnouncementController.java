package com.goodFinger.GoodFingerAnnouncementApplication.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goodFinger.GoodFingerAnnouncementApplication.service.AnnouncementServiceImpl;

@RestController
public class AnnouncementController {
	
	@Resource(name = "AnnouncementService")
	private AnnouncementServiceImpl announcementServiceImpl;
	
	@GetMapping(path = "/hello")
	public String sayHello() {
		return "helloWorld";
	}
	
	@GetMapping(path = "/getHello")
	public String helloSpringWorld() {
		return announcementServiceImpl.testFunc();
	}
}