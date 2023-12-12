package com.cms.payment;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class PaypalConfig {

	private String clientId;
	private String clientSecret;
	private String mode;

	public PaypalConfig(String clientId, String clientSecret, String mode) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.mode = mode;
	}

	public Map<String, String> paypalSdkConfig() {
		Map<String, String> config = new HashMap<>();
		config.put("mode", mode);
		return config;
	}

	public OAuthTokenCredential oAuthTokenCredential() {
		return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
	}

    @Bean
	public APIContext apiContext() throws PayPalRESTException {
		APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
		context.setConfigurationMap(paypalSdkConfig());
		return context;
	}
}
