package com.cms.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "associate")
public class Associate {

	private String associateId;	
	private String associateName;	
	private String associateAddress;	
	private String associateEmailId;

}
