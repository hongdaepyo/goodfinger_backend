package com.goodFinger.GoodFingerAnnouncementApplication.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.goodFinger.GoodFingerAnnouncementApplication.document.Announcement;
import com.goodFinger.GoodFingerAnnouncementApplication.payload.FileUploadResponse;
import com.goodFinger.GoodFingerAnnouncementApplication.service.AnnouncementFileUploadDownloadService;
import com.goodFinger.GoodFingerAnnouncementApplication.service.AnnouncementServiceImpl;

@RestController
@RequestMapping("/announcement")
public class AnnouncementFileController {
	private Logger logger = LogManager.getLogger(AnnouncementFileController.class);
	
	@Autowired
	private AnnouncementFileUploadDownloadService service;
	
	@PostMapping("/uploadFile")
	public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
		logger.debug("uploadFile started");
		String fileName = "";
		try {
			fileName = service.storeFile(file);
		} catch (Exception e) {
			logger.debug("uploadFile error");
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
								.path("/downloadFile/")
								.path(fileName)
								.toUriString();
		
		logger.debug("uploadFile ended");
		
		return new FileUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@PostMapping("uploadMultipleFiles")
	public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		logger.debug("uploadMultipleFiles started");
		
		logger.debug("uploadMultipleFiles ended");
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
		logger.debug("downloadFile started");
		
		Resource resource = service.loadFileAsResource(fileName);
		
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			logger.debug("Could not determine file type.");
		}
		
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		logger.debug("downloadFile ended");
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}