package com.goodFinger.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goodFinger.service.AnnouncementServiceImpl;

@RestController
public class AnnouncementController {
	
	private AnnouncementServiceImpl announcementServiceImpl;
	
	@PostMapping("/hello")
	public String sayHello(@RequestParam("name") String name, Model model) {
		model.addAttribute("name", name);
		return "hello";
	}
	
	@GetMapping(path = "/getHello")
	public String helloSpringWorld() {
		return announcementServiceImpl.testFunc();
	}
}