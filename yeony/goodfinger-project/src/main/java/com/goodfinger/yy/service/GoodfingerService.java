package com.goodfinger.yy.service;

import java.util.List;
import com.goodfinger.yy.repository.Company;

public interface GoodfingerService {
	public List<Company> getCompanyAll();
	public Company getCompanyByComId(String comId);
	public void insertCompany(Company com);
	public String insertCompany() throws Exception;
	public String updateCompany(Company com); 
}
