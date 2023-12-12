package com.cms.payment;

import lombok.Data;

@Data
public class Order {

	private double price;
	private String currency;
	private String method;
	private String intent;
	private String description;

}
