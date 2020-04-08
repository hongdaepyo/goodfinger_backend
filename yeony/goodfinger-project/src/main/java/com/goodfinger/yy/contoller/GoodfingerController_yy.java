package com.goodfinger.yy.contoller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goodfinger.yy.repository.Company;
import com.goodfinger.yy.repository.CompanyRepository;
import com.goodfinger.yy.service.GoodfingerService;
import com.goodfinger.yy.service.GoodfingerServiceImpl;

@RestController
@RequestMapping("/com")
public class GoodfingerController_yy {
	Logger log = LoggerFactory.getLogger(GoodfingerController_yy.class);

	@Autowired
	private CompanyRepository repositoryCom;
	
	@Resource(name = "GoodfingerService")
	private GoodfingerService service ;
	
	@Value("${upload.path}")
	private String filePathRoot;
	
	@CrossOrigin(origins = "*")
	@GetMapping("company")
	public ResponseEntity getCompany(@RequestParam(value = "comId") String comId) throws Exception{
		log.debug("getCompany start.");
		log.debug("comId:'" + comId + "'");
		
		Company com = service.getCompanyByComId(comId);
		if(com == null){
			return new ResponseEntity(com, HttpStatus.NO_CONTENT);
		}
		log.debug("getCompany end.");
		return new ResponseEntity(com, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param request
	 * {
			"comid":"yycom2",
			"location":"서울시강남구",
			"mastername":"박예연",
			"name":"예연회사2"
		}
	 * @param comstring
	 * @param files
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@PostMapping(value="insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity insertCompany(HttpServletRequest request, @RequestParam String comstring, 
			@RequestParam(value="files", required = false) ArrayList<MultipartFile> files) {
		if (comstring == null || files == null) {
			new ResponseEntity("null", HttpStatus.GONE);
		}
		
		System.out.println("request" + request);
		System.out.println("comString" + comstring);
		
		if (files != null){
			for(MultipartFile file : files){
				System.out.println(file.getOriginalFilename());
			}
		}
		
		Company com = new Company();
		
		try {
			JSONParser jsonpar = new JSONParser();
			JSONObject insertCom = (JSONObject) jsonpar.parse(comstring);
			com.setcomid(insertCom.get("comid").toString());
			com.setLocation(insertCom.get("location").toString());
			com.setMastername(insertCom.get("mastername").toString());
			com.setName(insertCom.get("name").toString());
			
			List<String> filePath = new ArrayList<String>();
			for(MultipartFile file2 : files){
				String fileName = file2.getOriginalFilename();
				String originFilePath = filePathRoot + fileName;
				File fileData = new File(originFilePath);
				file2.transferTo(fileData);
				filePath.add(originFilePath);
			}
			
			com.setPicture(filePath);
			service.insertCompany(com);
		} catch ( Exception  e) {
			e.printStackTrace();
			new ResponseEntity("error", HttpStatus.GONE);
		} 
		
		Company successInsert = new Company();
		successInsert = repositoryCom.findBycomid(com.getcomid());
		if(successInsert == null){
			new ResponseEntity(successInsert, HttpStatus.GONE);
		}
		return new ResponseEntity(successInsert, HttpStatus.OK);
	}	
}
