package com.cms.proxy;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth-service",url = "http://localhost:9098/auth")
public interface AuthenticationAuthorizationProxy {
	 
}
