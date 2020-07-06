package com.goodFinger.GoodFingerAnnouncementApplication.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AnnouncementFileUploadDownloadService {
	
	public String storeFile(MultipartFile file) throws Exception;
	public Resource loadFileAsResource(String fileName) throws Exception;
		
}