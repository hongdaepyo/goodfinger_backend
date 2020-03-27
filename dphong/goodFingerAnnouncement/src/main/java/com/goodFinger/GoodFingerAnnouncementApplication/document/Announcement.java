package com.goodFinger.GoodFingerAnnouncementApplication.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javassist.SerialVersionUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "announcement")
@Getter @Setter
public class Announcement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String announcement_id;
	private String company;
	private int category;
	private String location_city;
	private String location_district;
	private int recruitment;
	private String preferred_sex;
	private int[] preferred_age = {10, 20 ,30};
	private String task;
	private String work_date;
	private String work_time;
	private String[] salary = {"day", "time", "month"};
	private String etc;
	private String joboffer;
	private String[] picture;
	private String memo;
	private String name;
	private String questions;
	private String applicant_questions;
	private String[] applicant;

}