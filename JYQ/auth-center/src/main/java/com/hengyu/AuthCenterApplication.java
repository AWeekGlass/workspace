package com.hengyu;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableHystrix
@EnableFeignClients
@SpringCloudApplication
public class AuthCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthCenterApplication.class, args);
	}
}
