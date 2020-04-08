package com.goodfinger.yy.repository;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Company")
public class Company {
	private String comid;
	private String name;
	private String location;
	private String mastername;
	private List<String> picture;
	
	public String getcomid() {
		return comid;
	}

	public void setcomid(String comid) {
		this.comid = comid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMastername() {
		return mastername;
	}

	public void setMastername(String mastername) {
		this.mastername = mastername;
	}

	public List<String> getPicture() {
		return picture;
	}

	public void setPicture(List<String> picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return String.format("{comid:'%s', name:'%s', location:'%s', mastername:'%s', picture:'%s'}", comid,name,location,mastername,picture);
	}
}
