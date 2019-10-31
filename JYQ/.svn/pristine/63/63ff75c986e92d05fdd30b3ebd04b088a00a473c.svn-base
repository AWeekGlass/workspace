package com.hengyu;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableHystrix
@EnableFeignClients
@SpringCloudApplication
public class UserCenterApplication {
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		simpleClientHttpRequestFactory.setConnectTimeout(5000);
		simpleClientHttpRequestFactory.setReadTimeout(5000);
		return new RestTemplate(simpleClientHttpRequestFactory);
	}

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}
}
