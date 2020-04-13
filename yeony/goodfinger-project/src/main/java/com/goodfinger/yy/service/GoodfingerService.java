package com.goodfinger.yy.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.goodfinger.yy.repository.Company;

public interface GoodfingerService {
	public List<Company> getCompanyAll();
	public Company getCompanyByComId(String comId);
	public Company insertCompany(JSONObject param);
	public String insertCompany() throws Exception;
	public String updateCompany(Company com);
}
