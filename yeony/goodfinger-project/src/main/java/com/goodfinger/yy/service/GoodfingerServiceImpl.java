package com.goodfinger.yy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goodfinger.yy.repository.Company;
import com.goodfinger.yy.repository.CompanyRepository;

@Component("GoodfingerService")
@Service
public class GoodfingerServiceImpl implements GoodfingerService{
	Logger log = LoggerFactory.getLogger(GoodfingerServiceImpl.class);

	@Autowired
	CompanyRepository repositoryCom;
	
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
	public void insertCompany(Company com) {
		log.debug("start insertCompany.");
		repositoryCom.save(com);
		log.debug("end insertCompany.");
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
