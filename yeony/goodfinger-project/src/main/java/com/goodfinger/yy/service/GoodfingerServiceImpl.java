package com.goodfinger.yy.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodfinger.yy.dao.GoodfingerDao;
import com.goodfinger.yy.repository.Company;
import com.goodfinger.yy.repository.CompanyRepository;

@Component("GoodfingerService")
@Service
public class GoodfingerServiceImpl implements GoodfingerService{
	Logger log = LoggerFactory.getLogger(GoodfingerServiceImpl.class);

	@Autowired
	CompanyRepository repositoryCom;
	
	@Autowired
	GoodfingerDao dao;
	
	@Override
	public List<Company> getCompanyAll() {
		List<Company> com = repositoryCom.findAll();
		return com;
	}
	
	@Override
	public Company getCompanyByComId(String comId) {
		Company com = repositoryCom.findBycomid(comId);
		return com;
	}
	
	@Override
	public Company insertCompany(JSONObject param) {
		String comstring = param.get("comString").toString();
		ArrayList<MultipartFile> files = (ArrayList<MultipartFile>) param.get("files");
		String filePathRoot = param.get("filePathRoot").toString();
		
		Company com = new Company();
		Company successInsert = new Company();
		
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
				System.out.println(originFilePath);
				File fileData = new File(originFilePath);
				file2.transferTo(fileData);
				filePath.add(originFilePath);
			}
			com.setPicture(filePath);
			
			dao.addCompany(com);
			
		} catch ( Exception  e) {
			e.printStackTrace();
		} 
		successInsert = dao.findByCompanyId(com.getcomid());
		
		return successInsert;
	}
	
	@Override
	public String updateCompany(Company com) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertCompany() throws Exception {
		Company com = new Company();
//		log.debug("start insertCompany.");
//		com.setcomid("yycom");
//		com.setLocation("위치");
//		com.setMastername("박예연");
//		com.setName("예연회사");
//		com.setPicture("사진여러개들어갈건데");
		repositoryCom.save(com);
		log.debug("end insertCompany.");
		return "";
	}
	
}
