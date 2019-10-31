package com.hengyu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hengyu.service.impl.UserServiceImpl;

@FeignClient(value = "USER-CENTER", fallback = UserServiceImpl.class)
public interface UserService {
	@GetMapping("admin/login")
	String login(@RequestParam("phone") String phone,@RequestParam("password")  String password);
	
	@GetMapping("admin/check")
	String check(@RequestParam("phone") String phone);
}
