package com.cms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@JsonIgnoreProperties
@Document(collection = "associate")
public class Associate {

	@Id
	private String associateId;	
	private String associateName;	
	private String associateAddress;	
	private String associateEmailId;

}
