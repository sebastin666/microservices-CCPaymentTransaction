package com.itc.trn.mis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
public class CcPaymentTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcPaymentTransactionApplication.class, args);
	}
	
	@Bean
	@Primary
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@LoadBalanced
	public WebClient.Builder getWebClicentBuilder() {
		return WebClient.builder();
	}
	
	//
	
	@Bean(name="restTemplate_TO")
	@LoadBalanced
	public RestTemplate getRestTemplate_TO() {
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpComponentsClientHttpRequestFactory.setConnectTimeout(3000);
		httpComponentsClientHttpRequestFactory.setReadTimeout(3000);
		return new RestTemplate(httpComponentsClientHttpRequestFactory);
	}

}
