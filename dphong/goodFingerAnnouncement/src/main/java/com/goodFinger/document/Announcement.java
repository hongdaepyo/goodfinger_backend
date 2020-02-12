package com.goodFinger.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javassist.SerialVersionUID;
import lombok.Data;

@Data
@Document(collection = "announcement")
public class Announcement implements Serializable {
	
	private static final long SerialVersionUID = 1L;
	
	@Id
	private String id;
	private String name;
	
}