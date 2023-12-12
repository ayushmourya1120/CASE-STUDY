package com.cms.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "admission")
public class Admission implements Serializable{

	@Id
	private long registrationId;
	private String courseId;
	private String associateId;
	private int fees;	
	private String feedback	;
	
}




