package com.goodfinger.yy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goodfinger.yy.repository.Company;
import com.goodfinger.yy.repository.CompanyRepository;

@Repository
public class GoodfingerDao {
	@Autowired
	CompanyRepository repositoryCom;
	
	public void addCompany(Company com){
		repositoryCom.save(com);
	}
	
	public Company findByCompanyId(String comId){
		return repositoryCom.findBycomid(comId);
	}
}
